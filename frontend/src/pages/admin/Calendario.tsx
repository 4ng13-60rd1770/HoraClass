import { eventosPorDia, mockEventosCalendario } from '../../mocks/adminData'
import './admin-pages.css'

const weekLabels = ['Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb', 'Dom']

const buildMonthGrid = (year: number, month0: number) => {
  const first = new Date(year, month0, 1)
  const last = new Date(year, month0 + 1, 0)
  const padStart = (first.getDay() + 6) % 7
  const daysInMonth = last.getDate()
  const cells: { day: number; inMonth: boolean; isToday: boolean }[] = []

  const today = new Date()
  const isToday = (d: number) =>
    d === today.getDate() &&
    month0 === today.getMonth() &&
    year === today.getFullYear()

  const prevLast = new Date(year, month0, 0).getDate()
  for (let i = padStart - 1; i >= 0; i--) {
    cells.push({ day: prevLast - i, inMonth: false, isToday: false })
  }
  for (let d = 1; d <= daysInMonth; d++) {
    cells.push({ day: d, inMonth: true, isToday: isToday(d) })
  }
  const remaining = (7 - (cells.length % 7)) % 7
  for (let d = 1; d <= remaining; d++) {
    cells.push({ day: d, inMonth: false, isToday: false })
  }
  return cells
}

const Calendario = () => {
  const now = new Date()
  const year = now.getFullYear()
  const month = now.getMonth()
  const monthName = now.toLocaleString('es', { month: 'long', year: 'numeric' })
  const cells = buildMonthGrid(year, month)

  const eventosMes = mockEventosCalendario.filter((e) => {
    const [y, m] = e.fecha.split('-').map(Number)
    return y === year && m === month + 1
  })

  return (
    <>
      <h1 className="admin-page-title">Calendario</h1>
      <p className="admin-page-desc">
        Eventos y recordatorios académicos
      </p>
      <div className="admin-panel">
        <div className="calendar-toolbar">
          <h2 className="calendar-toolbar__month" style={{ textTransform: 'capitalize' }}>
            {monthName}
          </h2>
          <span className="admin-muted-caption">{eventosMes.length} eventos este mes</span>
        </div>
        <div className="calendar-weekdays">
          {weekLabels.map((w) => (
            <span key={w}>{w}</span>
          ))}
        </div>
        <div className="calendar-grid">
          {cells.map((c, i) => {
            const dayEvents =
              c.inMonth ? eventosPorDia(year, month, c.day) : []
            const hasEvents = dayEvents.length > 0

            return (
              <div
                key={`${c.day}-${c.inMonth}-${i}`}
                className={`calendar-day${!c.inMonth ? ' calendar-day--muted' : ''}${c.isToday ? ' calendar-day--today' : ''}${hasEvents ? ' calendar-day--events' : ''}`}
                title={
                  hasEvents
                    ? dayEvents.map((e) => `${e.hora} ${e.titulo}`).join(' · ')
                    : undefined
                }
              >
                <span className="calendar-day__num">{c.day}</span>
                {hasEvents ? (
                  <span className="calendar-day__dots" aria-hidden>
                    {dayEvents.slice(0, 3).map((e) => (
                      <span
                        key={e.titulo + e.hora}
                        className={`calendar-dot calendar-dot--${e.tipo}`}
                      />
                    ))}
                  </span>
                ) : null}
              </div>
            )
          })}
        </div>
      </div>
      {eventosMes.length > 0 ? (
        <div className="admin-panel admin-panel--mt">
          <h2>Eventos del mes</h2>
          <ul className="admin-event-list">
            {eventosMes.map((e) => (
              <li key={e.fecha + e.titulo} className="admin-event-row">
                <time dateTime={e.fecha} className="admin-event-row__date">
                  {new Date(e.fecha + 'T12:00:00').toLocaleDateString('es', {
                    day: 'numeric',
                    month: 'short',
                  })}
                </time>
                <span className="admin-event-row__hora">{e.hora}</span>
                <span className="admin-event-row__titulo">{e.titulo}</span>
                <span className={`admin-badge admin-badge--${e.tipo}`}>{e.tipo}</span>
              </li>
            ))}
          </ul>
        </div>
      ) : null}
    </>
  )
}

export default Calendario
