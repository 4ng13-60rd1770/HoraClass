/** Datos de demostración para el panel admin (sustituir por llamadas API). */

const pad2 = (n: number) => String(n).padStart(2, '0')

/** Fecha local en formato ISO YYYY-MM-DD. */
export function toIsoDate(d: Date): string {
  return `${d.getFullYear()}-${pad2(d.getMonth() + 1)}-${pad2(d.getDate())}`
}

function addDays(base: Date, days: number): Date {
  const x = new Date(base)
  x.setDate(x.getDate() + days)
  return x
}

/** Día `dom` del mes de `ref` (acotado al último día del mes). */
function dayInMonth(dom: number, ref: Date = new Date()): string {
  const y = ref.getFullYear()
  const m = ref.getMonth()
  const lastDom = new Date(y, m + 1, 0).getDate()
  const d = Math.min(Math.max(1, dom), lastDom)
  return `${y}-${pad2(m + 1)}-${pad2(d)}`
}

const _hoy = new Date()

export type MockDashboardStats = {
  sesionesHoy: number
  profesoresActivos: number
  estudiantes: number
  aulasEnUso: number
}

export const mockDashboardStats: MockDashboardStats = {
  sesionesHoy: 14,
  profesoresActivos: 26,
  estudiantes: 442,
  aulasEnUso: 8,
}

export type MockActividad = {
  id: string
  texto: string
  cuando: string
}

export const mockActividadesRecientes: MockActividad[] = [
  {
    id: '1',
    texto: 'Sesión “Física II” reprogramada al Aula 204',
    cuando: 'Hace 25 min',
  },
  {
    id: '2',
    texto: 'Nuevo estudiante matriculado en Ingeniería de Sistemas — Grupo B',
    cuando: 'Hace 1 h',
  },
  {
    id: '3',
    texto: 'Prof. Martínez actualizó disponibilidad para la semana del 31 mar',
    cuando: 'Hace 3 h',
  },
  {
    id: '4',
    texto: 'Bloque 14:00–16:00 reservado para comité académico',
    cuando: 'Ayer',
  },
]

export type MockProfesor = {
  id: string
  nombres: string
  apellidos: string
  email: string
  especialidad: string
  cargaHorasSemana: number
  activo: boolean
  /** URL de foto (mock con Picsum; en producción vendría del backend). */
  fotoUrl: string
}

export const mockProfesores: MockProfesor[] = [
  {
    id: 'p1',
    nombres: 'Carolina',
    apellidos: 'Martínez López',
    email: 'c.martinez@unbosque.edu.co',
    especialidad: 'Matemáticas aplicadas',
    cargaHorasSemana: 18,
    activo: true,
    fotoUrl: 'https://picsum.photos/seed/horaclass-p1/480/560',
  },
  {
    id: 'p2',
    nombres: 'Andrés',
    apellidos: 'Vega Ruíz',
    email: 'a.vega@unbosque.edu.co',
    especialidad: 'Física',
    cargaHorasSemana: 22,
    activo: true,
    fotoUrl: 'https://picsum.photos/seed/horaclass-p2/480/560',
  },
  {
    id: 'p3',
    nombres: 'María José',
    apellidos: 'Torres',
    email: 'mj.torres@unbosque.edu.co',
    especialidad: 'Programación',
    cargaHorasSemana: 20,
    activo: true,
    fotoUrl: 'https://picsum.photos/seed/horaclass-p3/480/560',
  },
  {
    id: 'p4',
    nombres: 'Luis',
    apellidos: 'Herrera',
    email: 'l.herrera@unbosque.edu.co',
    especialidad: 'Base de datos',
    cargaHorasSemana: 16,
    activo: true,
    fotoUrl: 'https://picsum.photos/seed/horaclass-p4/480/560',
  },
  {
    id: 'p5',
    nombres: 'Paula',
    apellidos: 'Gómez Silva',
    email: 'p.gomez@unbosque.edu.co',
    especialidad: 'Inglés técnico',
    cargaHorasSemana: 12,
    activo: false,
    fotoUrl: 'https://picsum.photos/seed/horaclass-p5/480/560',
  },
]

export type MockEstudiante = {
  id: string
  documento: string
  nombres: string
  apellidos: string
  programa: string
  grupo: string
  semestre: number
}

export const mockEstudiantes: MockEstudiante[] = [
  {
    id: 'e1',
    documento: '1005239012',
    nombres: 'Angie',
    apellidos: 'Gordillo',
    programa: 'Ingeniería de Sistemas',
    grupo: 'IS-301',
    semestre: 6,
  },
  {
    id: 'e2',
    documento: '1005238890',
    nombres: 'Diego',
    apellidos: 'Rincón',
    programa: 'Ingeniería de Sistemas',
    grupo: 'IS-301',
    semestre: 6,
  },
  {
    id: 'e3',
    documento: '1005123401',
    nombres: 'Valentina',
    apellidos: 'Ospina',
    programa: 'Administración',
    grupo: 'ADM-102',
    semestre: 2,
  },
  {
    id: 'e4',
    documento: '1005987654',
    nombres: 'Santiago',
    apellidos: 'Peña',
    programa: 'Ingeniería Industrial',
    grupo: 'II-205',
    semestre: 4,
  },
  {
    id: 'e5',
    documento: '1005342110',
    nombres: 'Laura',
    apellidos: 'Castillo',
    programa: 'Psicología',
    grupo: 'PSI-401',
    semestre: 8,
  },
  {
    id: 'e6',
    documento: '1005456789',
    nombres: 'Nicolás',
    apellidos: 'Fajardo',
    programa: 'Ingeniería de Sistemas',
    grupo: 'IS-302',
    semestre: 5,
  },
]

export type JornadaGrupo = 'mañana' | 'tarde' | 'noche'

export type MockGrupo = {
  id: string
  codigo: string
  nombre: string
  programa: string
  semestre: number
  cupoMaximo: number
  jornada: JornadaGrupo
  activo: boolean
  observaciones?: string
  /** IDs de cursos del catálogo (`mockCursosCatalogo`) asociados al grupo. */
  cursosAsignados: string[]
}

/** Catálogo de cursos ofrecidos por programa (mock). */
export type MockCursoCatalogo = {
  id: string
  codigo: string
  nombre: string
  programa: string
  semestre: number
  creditos: number
}

export const mockCursosCatalogo: MockCursoCatalogo[] = [
  {
    id: 'cur-is-ed',
    codigo: 'IS-ED501',
    nombre: 'Estructuras de datos',
    programa: 'Ingeniería de Sistemas',
    semestre: 5,
    creditos: 3,
  },
  {
    id: 'cur-is-bd',
    codigo: 'IS-BD601',
    nombre: 'Bases de datos',
    programa: 'Ingeniería de Sistemas',
    semestre: 6,
    creditos: 4,
  },
  {
    id: 'cur-is-ayd',
    codigo: 'IS-AYD602',
    nombre: 'Arquitectura y desarrollo web',
    programa: 'Ingeniería de Sistemas',
    semestre: 6,
    creditos: 3,
  },
  {
    id: 'cur-is-ing',
    codigo: 'IS-ING501',
    nombre: 'Ingeniería de software I',
    programa: 'Ingeniería de Sistemas',
    semestre: 5,
    creditos: 3,
  },
  {
    id: 'cur-adm-cont',
    codigo: 'ADM-CON102',
    nombre: 'Contabilidad general',
    programa: 'Administración',
    semestre: 2,
    creditos: 4,
  },
  {
    id: 'cur-adm-mkt',
    codigo: 'ADM-MKT201',
    nombre: 'Fundamentos de mercadeo',
    programa: 'Administración',
    semestre: 2,
    creditos: 3,
  },
  {
    id: 'cur-ii-proc',
    codigo: 'II-PROC401',
    nombre: 'Procesos industriales',
    programa: 'Ingeniería Industrial',
    semestre: 4,
    creditos: 4,
  },
  {
    id: 'cur-ii-erg',
    codigo: 'II-ERG305',
    nombre: 'Ergonomía',
    programa: 'Ingeniería Industrial',
    semestre: 4,
    creditos: 2,
  },
  {
    id: 'cur-psi-pam',
    codigo: 'PSI-PAM801',
    nombre: 'Psicología clínica',
    programa: 'Psicología',
    semestre: 8,
    creditos: 5,
  },
]

/** Programas académicos disponibles al crear un grupo (mock). */
export const opcionesProgramaGrupo: string[] = [
  'Ingeniería de Sistemas',
  'Administración',
  'Ingeniería Industrial',
  'Psicología',
  'Derecho',
  'Medicina',
]

export const mockGruposSemilla: MockGrupo[] = [
  {
    id: 'g1',
    codigo: 'IS-301',
    nombre: 'Sistemas — Tarde A',
    programa: 'Ingeniería de Sistemas',
    semestre: 6,
    cupoMaximo: 40,
    jornada: 'tarde',
    activo: true,
    cursosAsignados: ['cur-is-bd', 'cur-is-ayd'],
  },
  {
    id: 'g2',
    codigo: 'IS-302',
    nombre: 'Sistemas — Mañana B',
    programa: 'Ingeniería de Sistemas',
    semestre: 5,
    cupoMaximo: 38,
    jornada: 'mañana',
    activo: true,
    cursosAsignados: ['cur-is-ed', 'cur-is-ing'],
  },
  {
    id: 'g3',
    codigo: 'ADM-102',
    nombre: 'Administración nocturna',
    programa: 'Administración',
    semestre: 2,
    cupoMaximo: 45,
    jornada: 'noche',
    activo: true,
    cursosAsignados: ['cur-adm-cont', 'cur-adm-mkt'],
  },
  {
    id: 'g4',
    codigo: 'II-205',
    nombre: 'Industrial — fin de semana',
    programa: 'Ingeniería Industrial',
    semestre: 4,
    cupoMaximo: 32,
    jornada: 'mañana',
    activo: false,
    cursosAsignados: ['cur-ii-proc'],
  },
]

export function buscarCursoCatalogoPorId(id: string): MockCursoCatalogo | undefined {
  return mockCursosCatalogo.find((c) => c.id === id)
}

export type MockSesion = {
  id: string
  curso: string
  profesor: string
  aula: string
  fecha: string
  horaInicio: string
  horaFin: string
  estado: 'confirmada' | 'pendiente' | 'cancelada'
}

export const mockSesiones: MockSesion[] = [
  {
    id: 's1',
    curso: 'Álgebra lineal — G101',
    profesor: 'C. Martínez',
    aula: 'A-201',
    fecha: toIsoDate(_hoy),
    horaInicio: '07:00',
    horaFin: '09:00',
    estado: 'confirmada',
  },
  {
    id: 's2',
    curso: 'Física II — Lab',
    profesor: 'A. Vega',
    aula: 'LAB-F2',
    fecha: toIsoDate(_hoy),
    horaInicio: '09:00',
    horaFin: '12:00',
    estado: 'confirmada',
  },
  {
    id: 's3',
    curso: 'Estructuras de datos — IS-301',
    profesor: 'M.J. Torres',
    aula: 'B-105',
    fecha: toIsoDate(_hoy),
    horaInicio: '14:00',
    horaFin: '17:00',
    estado: 'pendiente',
  },
  {
    id: 's4',
    curso: 'Bases de datos — IS-302',
    profesor: 'L. Herrera',
    aula: 'C-310',
    fecha: toIsoDate(addDays(_hoy, 1)),
    horaInicio: '08:00',
    horaFin: '11:00',
    estado: 'confirmada',
  },
  {
    id: 's5',
    curso: 'Inglés IV — ADM',
    profesor: 'P. Gómez',
    aula: 'A-118',
    fecha: toIsoDate(addDays(_hoy, 1)),
    horaInicio: '15:00',
    horaFin: '17:00',
    estado: 'cancelada',
  },
  {
    id: 's6',
    curso: 'Cálculo integral — G102',
    profesor: 'C. Martínez',
    aula: 'A-201',
    fecha: toIsoDate(addDays(_hoy, 4)),
    horaInicio: '10:00',
    horaFin: '12:00',
    estado: 'confirmada',
  },
]

export type MockEventoCalendario = {
  fecha: string
  titulo: string
  hora: string
  tipo: 'clase' | 'examen' | 'reunion'
}

/** Eventos del mes actual (según fecha del navegador al cargar el módulo). */
export const mockEventosCalendario: MockEventoCalendario[] = [
  {
    fecha: dayInMonth(3, _hoy),
    titulo: 'Comité de programa — Sistemas',
    hora: '09:00',
    tipo: 'reunion',
  },
  { fecha: dayInMonth(5, _hoy), titulo: 'Examen parcial Álgebra', hora: '07:00', tipo: 'examen' },
  {
    fecha: dayInMonth(10, _hoy),
    titulo: 'Taller PostgreSQL (optativa)',
    hora: '14:00',
    tipo: 'clase',
  },
  { fecha: dayInMonth(14, _hoy), titulo: 'Examen Física II', hora: '08:00', tipo: 'examen' },
  {
    fecha: dayInMonth(18, _hoy),
    titulo: 'Entrega proyecto Estructuras',
    hora: '23:59',
    tipo: 'examen',
  },
  {
    fecha: dayInMonth(22, _hoy),
    titulo: 'Inducción docente nuevo ingreso',
    hora: '10:00',
    tipo: 'reunion',
  },
  {
    fecha: dayInMonth(27, _hoy),
    titulo: 'Jornada de mentorías',
    hora: '15:00',
    tipo: 'reunion',
  },
  { fecha: dayInMonth(28, _hoy), titulo: 'Open house facultad', hora: '11:00', tipo: 'reunion' },
]

export function eventosPorDia(
  year: number,
  monthIndex: number,
  day: number,
): MockEventoCalendario[] {
  const m = String(monthIndex + 1).padStart(2, '0')
  const d = String(day).padStart(2, '0')
  const key = `${year}-${m}-${d}`
  return mockEventosCalendario.filter((e) => e.fecha === key)
}
