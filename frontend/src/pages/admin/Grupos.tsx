import { type FormEvent, useMemo, useRef, useState } from 'react'
import {
  type JornadaGrupo,
  type MockGrupo,
  buscarCursoCatalogoPorId,
  mockCursosCatalogo,
  mockGruposSemilla,
  opcionesProgramaGrupo,
} from '../../mocks/adminData'
import {
  type GrupoFormValores,
  type RegistroEventoGrupo,
  hayErroresGrupo,
  normalizarCodigoGrupo,
  validarAsociacionGrupoCurso,
  validarGrupoAcademico,
} from '../../utils/grupoValidation'
import './admin-pages.css'
import './Grupos.css'

const jornadaLabels: Record<JornadaGrupo, string> = {
  mañana: 'Mañana',
  tarde: 'Tarde',
  noche: 'Noche',
}

const nuevoId = () =>
  typeof crypto !== 'undefined' && crypto.randomUUID
    ? crypto.randomUUID()
    : `id-${Date.now()}-${Math.random().toString(36).slice(2, 9)}`

const estadoInicialForm = (): GrupoFormValores => ({
  codigo: '',
  nombre: '',
  programa: opcionesProgramaGrupo[0] ?? '',
  semestre: '1',
  cupoMaximo: '35',
  observaciones: '',
})

const formatoInstante = (iso: string) =>
  new Date(iso).toLocaleString('es-CO', {
    dateStyle: 'short',
    timeStyle: 'medium',
  })

const Grupos = () => {
  const idsCreadosEnSesion = useRef<Set<string>>(new Set())
  const [grupos, setGrupos] = useState<MockGrupo[]>(() =>
    mockGruposSemilla.map((g) => ({ ...g, cursosAsignados: [...g.cursosAsignados] })),
  )
  const [form, setForm] = useState<GrupoFormValores>(() => estadoInicialForm())

  const [formJornada, setFormJornada] = useState<JornadaGrupo>('mañana')
  const [formActivo, setFormActivo] = useState(true)
  const [fieldErrors, setFieldErrors] = useState<
    Partial<Record<keyof GrupoFormValores | 'general', string>>
  >({})
  const [feedback, setFeedback] = useState<{ tipo: 'ok' | 'err'; msg: string } | null>(null)

  const [registro, setRegistro] = useState<RegistroEventoGrupo[]>([])

  const [assocGrupoId, setAssocGrupoId] = useState<string>(() => mockGruposSemilla[0]?.id ?? '')
  const [assocCursoId, setAssocCursoId] = useState<string>('')
  const [assocMsg, setAssocMsg] = useState<{ tipo: 'ok' | 'err'; text: string } | null>(null)

  const agregarRegistro = (evt: RegistroEventoGrupo) => {
    setRegistro((prev) => [evt, ...prev])
  }

  const codigosExistentes = useMemo(
    () => new Set(grupos.map((g) => g.codigo.toUpperCase())),
    [grupos],
  )

  const grupoSeleccionado = grupos.find((g) => g.id === assocGrupoId)
  const cursosFiltrados = useMemo(() => {
    if (!grupoSeleccionado) return []
    return mockCursosCatalogo.filter((c) => c.programa === grupoSeleccionado.programa)
  }, [grupoSeleccionado])

  const handleSubmitGrupo = (e: FormEvent) => {
    e.preventDefault()
    setFeedback(null)

    const valores: GrupoFormValores = {
      codigo: form.codigo,
      nombre: form.nombre,
      programa: form.programa,
      semestre: form.semestre,
      cupoMaximo: form.cupoMaximo,
      observaciones: form.observaciones,
    }

    const errores = validarGrupoAcademico(valores, opcionesProgramaGrupo, codigosExistentes)
    setFieldErrors(errores)

    if (hayErroresGrupo(errores)) {
      const primero =
        errores.codigo ??
        errores.nombre ??
        errores.programa ??
        errores.semestre ??
        errores.cupoMaximo ??
        errores.observaciones ??
        'Revisa los datos del formulario.'
      setFeedback({ tipo: 'err', msg: primero })
      return
    }

    const codigo = normalizarCodigoGrupo(valores.codigo)
    const semestre = Number.parseInt(valores.semestre, 10)
    const cupoMaximo = Number.parseInt(valores.cupoMaximo, 10)

    const nuevo: MockGrupo = {
      id: nuevoId(),
      codigo,
      nombre: valores.nombre.trim(),
      programa: valores.programa,
      semestre,
      cupoMaximo,
      jornada: formJornada,
      activo: formActivo,
      observaciones: valores.observaciones.trim() || undefined,
      cursosAsignados: [],
    }

    idsCreadosEnSesion.current.add(nuevo.id)
    setGrupos((prev) => [nuevo, ...prev])
    setForm(estadoInicialForm())
    setFormJornada('mañana')
    setFormActivo(true)
    setFieldErrors({})

    agregarRegistro({
      id: nuevoId(),
      tipo: 'CREACION_GRUPO',
      instante: new Date().toISOString(),
      resumen: `Grupo académico creado: ${nuevo.codigo} — ${nuevo.nombre} (${nuevo.programa}, sem. ${nuevo.semestre}).`,
      grupoCodigo: nuevo.codigo,
    })

    setFeedback({
      tipo: 'ok',
      msg: `Grupo «${codigo}» creado y registrado en el historial (sesión actual).`,
    })
  }

  const limpiarFormulario = () => {
    setForm(estadoInicialForm())
    setFormJornada('mañana')
    setFormActivo(true)
    setFieldErrors({})
    setFeedback(null)
  }

  const asociarCurso = () => {
    setAssocMsg(null)
    const grupo = grupos.find((g) => g.id === assocGrupoId)
    const curso = mockCursosCatalogo.find((c) => c.id === assocCursoId)
    const err = validarAsociacionGrupoCurso(
      grupo,
      curso,
      grupo?.cursosAsignados ?? [],
    )
    if (err) {
      setAssocMsg({ tipo: 'err', text: err })
      return
    }

    setGrupos((prev) =>
      prev.map((g) =>
        g.id === grupo!.id ? { ...g, cursosAsignados: [...g.cursosAsignados, curso!.id] } : g,
      ),
    )

    agregarRegistro({
      id: nuevoId(),
      tipo: 'ASOCIACION_CURSO',
      instante: new Date().toISOString(),
      resumen: `Curso ${curso!.codigo} asociado al grupo ${grupo!.codigo}.`,
      grupoCodigo: grupo!.codigo,
      cursoCodigo: curso!.codigo,
      cursoNombre: curso!.nombre,
    })

    setAssocCursoId('')
    setAssocMsg({
      tipo: 'ok',
      text: `«${curso!.nombre}» quedó asociado a ${grupo!.codigo}.`,
    })
  }

  const quitarCurso = (grupoId: string, cursoId: string) => {
    const grupo = grupos.find((g) => g.id === grupoId)
    const curso = buscarCursoCatalogoPorId(cursoId)
    if (!grupo || !curso) return

    setGrupos((prev) =>
      prev.map((g) =>
        g.id === grupoId
          ? { ...g, cursosAsignados: g.cursosAsignados.filter((id) => id !== cursoId) }
          : g,
      ),
    )

    agregarRegistro({
      id: nuevoId(),
      tipo: 'DESASOCIACION_CURSO',
      instante: new Date().toISOString(),
      resumen: `Curso ${curso.codigo} desasociado del grupo ${grupo.codigo}.`,
      grupoCodigo: grupo.codigo,
      cursoCodigo: curso.codigo,
      cursoNombre: curso.nombre,
    })
  }

  const limpiarCampoError = (campo: keyof GrupoFormValores) => {
    setFieldErrors((prev) => {
      const next = { ...prev }
      delete next[campo]
      return next
    })
  }

  return (
    <>
      <h1 className="admin-page-title">Grupos</h1>
      <p className="admin-page-desc">
        Creación de grupos con validaciones, registro de eventos (auditoría de sesión) y
        asociación de cursos del catálogo al mismo programa y semestre compatible.
      </p>

      <div className="grupos-layout">
        <div className="grupos-form-card">
          <h2>Nuevo grupo</h2>
          <form
            onSubmit={handleSubmitGrupo}
            className="grupos-form-grid"
            noValidate
          >
            {feedback ? (
              <p
                className={`grupos-feedback grupos-feedback--${feedback.tipo === 'ok' ? 'ok' : 'err'}`}
                role="status"
              >
                {feedback.msg}
              </p>
            ) : null}

            <div className="grupos-field">
              <label htmlFor="grupo-codigo">
                Código del grupo <span aria-hidden> *</span>
              </label>
              <span className="grupos-hint" id="grupo-codigo-hint">
                Letras y números; guiones permitidos. Ej. IS-303
              </span>
              <input
                id="grupo-codigo"
                name="codigo"
                value={form.codigo}
                onChange={(e) => {
                  limpiarCampoError('codigo')
                  setForm((f) => ({ ...f, codigo: e.target.value }))
                }}
                autoComplete="off"
                aria-invalid={Boolean(fieldErrors.codigo)}
                aria-describedby="grupo-codigo-hint grupo-codigo-err"
              />
              {fieldErrors.codigo ? (
                <span id="grupo-codigo-err" className="grupos-field-error" role="alert">
                  {fieldErrors.codigo}
                </span>
              ) : null}
            </div>

            <div className="grupos-field">
              <label htmlFor="grupo-semestre">
                Semestre <span aria-hidden> *</span>
              </label>
              <input
                id="grupo-semestre"
                name="semestre"
                type="text"
                inputMode="numeric"
                maxLength={2}
                value={form.semestre}
                onChange={(e) => {
                  limpiarCampoError('semestre')
                  setForm((f) => ({ ...f, semestre: e.target.value }))
                }}
                aria-invalid={Boolean(fieldErrors.semestre)}
              />
              {fieldErrors.semestre ? (
                <span className="grupos-field-error" role="alert">
                  {fieldErrors.semestre}
                </span>
              ) : null}
            </div>

            <div className="grupos-field grupos-field--full">
              <label htmlFor="grupo-nombre">
                Nombre interno <span aria-hidden> *</span>
              </label>
              <span className="grupos-hint" id="grupo-nombre-hint">
                Entre 3 y 120 caracteres
              </span>
              <input
                id="grupo-nombre"
                name="nombre"
                value={form.nombre}
                onChange={(e) => {
                  limpiarCampoError('nombre')
                  setForm((f) => ({ ...f, nombre: e.target.value }))
                }}
                aria-invalid={Boolean(fieldErrors.nombre)}
                aria-describedby="grupo-nombre-hint"
              />
              {fieldErrors.nombre ? (
                <span className="grupos-field-error" role="alert">
                  {fieldErrors.nombre}
                </span>
              ) : null}
            </div>

            <div className="grupos-field grupos-field--full">
              <label htmlFor="grupo-programa">Programa académico</label>
              <select
                id="grupo-programa"
                name="programa"
                value={form.programa}
                onChange={(e) => {
                  limpiarCampoError('programa')
                  setForm((f) => ({ ...f, programa: e.target.value }))
                }}
                aria-invalid={Boolean(fieldErrors.programa)}
              >
                {opcionesProgramaGrupo.map((p) => (
                  <option key={p} value={p}>
                    {p}
                  </option>
                ))}
              </select>
              {fieldErrors.programa ? (
                <span className="grupos-field-error" role="alert">
                  {fieldErrors.programa}
                </span>
              ) : null}
            </div>

            <div className="grupos-field">
              <label htmlFor="grupo-cupo">Cupo máximo</label>
              <input
                id="grupo-cupo"
                name="cupoMaximo"
                type="text"
                inputMode="numeric"
                maxLength={3}
                value={form.cupoMaximo}
                onChange={(e) => {
                  limpiarCampoError('cupoMaximo')
                  setForm((f) => ({ ...f, cupoMaximo: e.target.value }))
                }}
                aria-invalid={Boolean(fieldErrors.cupoMaximo)}
              />
              {fieldErrors.cupoMaximo ? (
                <span className="grupos-field-error" role="alert">
                  {fieldErrors.cupoMaximo}
                </span>
              ) : null}
            </div>

            <div className="grupos-field">
              <label htmlFor="grupo-jornada">Jornada</label>
              <select
                id="grupo-jornada"
                name="jornada"
                value={formJornada}
                onChange={(e) => setFormJornada(e.target.value as JornadaGrupo)}
              >
                {(Object.keys(jornadaLabels) as JornadaGrupo[]).map((j) => (
                  <option key={j} value={j}>
                    {jornadaLabels[j]}
                  </option>
                ))}
              </select>
            </div>

            <div className="grupos-field grupos-field--full grupos-check">
              <input
                id="grupo-activo"
                name="activo"
                type="checkbox"
                checked={formActivo}
                onChange={(e) => setFormActivo(e.target.checked)}
              />
              <label htmlFor="grupo-activo">Grupo activo (admite matrículas)</label>
            </div>

            <div className="grupos-field grupos-field--full">
              <label htmlFor="grupo-obs">Observaciones</label>
              <span className="grupos-hint">Máximo 500 caracteres</span>
              <textarea
                id="grupo-obs"
                name="observaciones"
                value={form.observaciones}
                onChange={(e) => {
                  limpiarCampoError('observaciones')
                  setForm((f) => ({ ...f, observaciones: e.target.value }))
                }}
                aria-invalid={Boolean(fieldErrors.observaciones)}
                placeholder="Opcional: sede, modalidad híbrida, notas para secretaría…"
              />
              {fieldErrors.observaciones ? (
                <span className="grupos-field-error" role="alert">
                  {fieldErrors.observaciones}
                </span>
              ) : null}
            </div>

            <div className="grupos-actions">
              <button type="submit" className="grupos-btn-primary">
                Crear grupo
              </button>
              <button type="button" className="grupos-btn-ghost" onClick={limpiarFormulario}>
                Limpiar formulario
              </button>
            </div>
          </form>
        </div>

        <aside className="grupos-aside" aria-label="Grupos registrados">
          <h2>Grupos y cursos</h2>
          <p className="grupos-aside-count">{grupos.length} grupos · más recientes arriba</p>
          <ul className="grupos-list">
            {grupos.map((g) => {
              const esNuevo = idsCreadosEnSesion.current.has(g.id)
              return (
                <li key={g.id} className="grupos-list-item">
                  <p className="grupos-list-code">
                    {g.codigo}
                    {!g.activo ? (
                      <span className="grupos-tag" style={{ marginInlineStart: '0.35rem' }}>
                        Inactivo
                      </span>
                    ) : null}
                    {esNuevo ? (
                      <span
                        className="grupos-tag grupos-tag--nuevo"
                        style={{ marginInlineStart: '0.35rem' }}
                      >
                        Nuevo
                      </span>
                    ) : null}
                  </p>
                  <p className="grupos-list-name">{g.nombre}</p>
                  <div className="grupos-list-meta">
                    <span>{g.programa}</span>
                    <span>
                      Sem. {g.semestre} · {jornadaLabels[g.jornada]}
                    </span>
                    <span>Cupo {g.cupoMaximo}</span>
                  </div>
                  <div className="grupos-cursos-chips">
                    {g.cursosAsignados.length === 0 ? (
                      <span className="grupos-sin-cursos">Sin cursos asociados</span>
                    ) : null}
                    {g.cursosAsignados.map((cid) => {
                      const c = buscarCursoCatalogoPorId(cid)
                      if (!c) return null
                      return (
                        <span key={cid} className="grupos-chip">
                          <span className="grupos-chip__text" title={c.nombre}>
                            {c.codigo}
                          </span>
                          <button
                            type="button"
                            className="grupos-chip__remove"
                            title={`Quitar ${c.nombre}`}
                            aria-label={`Desasociar curso ${c.codigo} del grupo ${g.codigo}`}
                            onClick={() => quitarCurso(g.id, cid)}
                          >
                            ×
                          </button>
                        </span>
                      )
                    })}
                  </div>
                </li>
              )
            })}
          </ul>
        </aside>
      </div>

      <div className="grupos-lower">
        <section className="grupos-panel" aria-labelledby="grupos-assoc-title">
          <h2 id="grupos-assoc-title">Asociar curso al grupo</h2>
          <p className="grupos-panel-desc">
            Solo se listan cursos del mismo programa. El semestre del curso no puede diferir
            más de 2 del semestre del grupo.
          </p>
          <div className="grupos-assoc-grid">
            <div className="grupos-field">
              <label htmlFor="assoc-grupo">Grupo</label>
              <select
                id="assoc-grupo"
                value={assocGrupoId}
                onChange={(e) => {
                  setAssocGrupoId(e.target.value)
                  setAssocCursoId('')
                  setAssocMsg(null)
                }}
              >
                {grupos.map((g) => (
                  <option key={g.id} value={g.id}>
                    {g.codigo} — {g.nombre}
                  </option>
                ))}
              </select>
            </div>
            <div className="grupos-field">
              <label htmlFor="assoc-curso">Curso</label>
              <select
                id="assoc-curso"
                value={assocCursoId}
                onChange={(e) => setAssocCursoId(e.target.value)}
              >
                <option value="">— Elegir curso —</option>
                {cursosFiltrados
                  .filter((c) => !grupoSeleccionado?.cursosAsignados.includes(c.id))
                  .map((c) => (
                    <option key={c.id} value={c.id}>
                      {c.codigo} · Sem. {c.semestre} — {c.nombre}
                    </option>
                  ))}
              </select>
            </div>
          </div>
          {assocMsg ? (
            <p
              className={`grupos-feedback grupos-feedback--${assocMsg.tipo === 'ok' ? 'ok' : 'err'} grupos-feedback--inline`}
              role="status"
            >
              {assocMsg.text}
            </p>
          ) : null}
          <button
            type="button"
            className="grupos-btn-primary"
            disabled={!assocCursoId || !grupoSeleccionado}
            onClick={asociarCurso}
          >
            Asociar curso seleccionado
          </button>
        </section>

        <section className="grupos-panel grupos-panel--audit" aria-labelledby="grupos-audit-title">
          <h2 id="grupos-audit-title">Registro de eventos</h2>
          <p className="grupos-panel-desc">
            Creación de grupos y cambios en la asignación de cursos en esta sesión (mock de
            auditoría).
          </p>
          {registro.length === 0 ? (
            <p className="grupos-audit-empty">Aún no hay eventos registrados.</p>
          ) : (
            <ul className="grupos-audit-list">
              {registro.map((r) => (
                <li key={r.id} className={`grupos-audit-item grupos-audit-item--${r.tipo}`}>
                  <time className="grupos-audit-time" dateTime={r.instante}>
                    {formatoInstante(r.instante)}
                  </time>
                  <span className="grupos-audit-tipo">{r.tipo.replace(/_/g, ' ')}</span>
                  <p className="grupos-audit-resumen">{r.resumen}</p>
                </li>
              ))}
            </ul>
          )}
        </section>
      </div>
    </>
  )
}

export default Grupos
