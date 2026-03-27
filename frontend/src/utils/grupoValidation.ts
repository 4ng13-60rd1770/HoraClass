import type { MockCursoCatalogo, MockGrupo } from '../mocks/adminData'

export type GrupoFormValores = {
  codigo: string
  nombre: string
  programa: string
  semestre: string
  cupoMaximo: string
  observaciones: string
}

const CODIGO_REGEX = /^[A-Z0-9]+(-[A-Z0-9]+)*$/i

/** Errores por campo (clave = nombre del campo en el formulario). */
export type ErroresGrupoForm = Partial<
  Record<keyof GrupoFormValores | 'general', string>
>

export function normalizarCodigoGrupo(raw: string): string {
  return raw.trim().toUpperCase().replace(/\s+/g, '')
}

function validarNombreGrupo(raw: string): string | null {
  const n = raw.trim()
  if (!n) return 'El nombre interno es obligatorio.'
  if (n.length < 3) return 'El nombre debe tener al menos 3 caracteres.'
  if (n.length > 120) return 'El nombre no puede superar 120 caracteres.'
  return null
}

function validarCodigoGrupo(raw: string): string | null {
  const s = normalizarCodigoGrupo(raw)
  if (!s) return 'El código del grupo es obligatorio.'
  if (s.length < 4 || s.length > 20) {
    return 'El código debe tener entre 4 y 20 caracteres (sin espacios).'
  }
  if (!CODIGO_REGEX.test(s)) {
    return 'Use solo letras, números y guiones. Ejemplo: IS-303, ADM201.'
  }
  return null
}

function validarSemestre(raw: string): string | null {
  const t = raw.trim()
  if (!/^\d+$/.test(t)) return 'Indica un semestre entero (sin decimales).'
  const n = Number.parseInt(t, 10)
  if (n < 1 || n > 20) return 'El semestre debe estar entre 1 y 20.'
  return null
}

function validarCupo(raw: string): string | null {
  const t = raw.trim()
  if (!/^\d+$/.test(t)) return 'Indica un cupo entero (sin decimales).'
  const n = Number.parseInt(t, 10)
  if (n < 1 || n > 500) return 'El cupo debe estar entre 1 y 500.'
  return null
}

function validarObservaciones(raw: string): string | null {
  if (raw.length > 500) return 'Las observaciones no pueden superar 500 caracteres.'
  return null
}

/**
 * Valida el formulario de creación de grupo académico.
 */
export function validarGrupoAcademico(
  valores: GrupoFormValores,
  opcionesPrograma: readonly string[],
  codigosExistentesNormalizados: Set<string>,
): ErroresGrupoForm {
  const errores: ErroresGrupoForm = {}

  const errCod = validarCodigoGrupo(valores.codigo)
  if (errCod) errores.codigo = errCod
  else {
    const cod = normalizarCodigoGrupo(valores.codigo)
    if (codigosExistentesNormalizados.has(cod)) {
      errores.codigo = `Ya existe un grupo con el código «${cod}».`
    }
  }

  const errNom = validarNombreGrupo(valores.nombre)
  if (errNom) errores.nombre = errNom

  if (!opcionesPrograma.includes(valores.programa)) {
    errores.programa = 'Selecciona un programa académico válido.'
  }

  const errSem = validarSemestre(valores.semestre)
  if (errSem) errores.semestre = errSem

  const errCupo = validarCupo(valores.cupoMaximo)
  if (errCupo) errores.cupoMaximo = errCupo

  const errObs = validarObservaciones(valores.observaciones)
  if (errObs) errores.observaciones = errObs

  return errores
}

export function hayErroresGrupo(errores: ErroresGrupoForm): boolean {
  return Object.keys(errores).length > 0
}

/** Máxima diferencia de semestre permitida entre curso y grupo para asociar. */
const MAX_DIFF_SEMESTRE_CURSO = 2

/**
 * Devuelve mensaje de error o null si la asociación es válida.
 */
/** Entrada de auditoría UI (persistir vía API en producción). */
export type RegistroEventoGrupo = {
  id: string
  tipo: 'CREACION_GRUPO' | 'ASOCIACION_CURSO' | 'DESASOCIACION_CURSO'
  instante: string
  resumen: string
  grupoCodigo: string
  cursoCodigo?: string
  cursoNombre?: string
}

export function validarAsociacionGrupoCurso(
  grupo: MockGrupo | undefined,
  curso: MockCursoCatalogo | undefined,
  idsCursoYaAsociados: string[],
): string | null {
  if (!grupo) return 'Selecciona un grupo.'
  if (!curso) return 'Selecciona un curso.'
  if (grupo.programa !== curso.programa) {
    return 'El curso debe pertenecer al mismo programa académico que el grupo.'
  }
  if (Math.abs(curso.semestre - grupo.semestre) > MAX_DIFF_SEMESTRE_CURSO) {
    return `El semestre del curso (${curso.semestre}) no es compatible con el del grupo (${grupo.semestre}). Máx. diferencia: ${MAX_DIFF_SEMESTRE_CURSO}.`
  }
  if (idsCursoYaAsociados.includes(curso.id)) {
    return 'Este curso ya está asociado a ese grupo.'
  }
  return null
}
