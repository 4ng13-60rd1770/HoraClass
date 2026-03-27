import { mockSesiones } from '../../mocks/adminData'
import './admin-pages.css'

const estadoLabel: Record<string, string> = {
  confirmada: 'Confirmada',
  pendiente: 'Pendiente',
  cancelada: 'Cancelada',
}

const Sesiones = () => {
  return (
    <>
      <h1 className="admin-page-title">Sesiones</h1>
      <p className="admin-page-desc">
        Horarios y asignación 
      </p>
      <div className="admin-table-wrap">
        <table className="admin-table">
          <thead>
            <tr>
              <th>Curso</th>
              <th>Profesor</th>
              <th>Aula</th>
              <th>Fecha</th>
              <th>Horario</th>
              <th>Estado</th>
            </tr>
          </thead>
          <tbody>
            {[...mockSesiones]
              .sort(
                (a, b) =>
                  a.fecha.localeCompare(b.fecha) ||
                  a.horaInicio.localeCompare(b.horaInicio),
              )
              .map((row) => (
                <tr key={row.id}>
                  <td>{row.curso}</td>
                  <td>{row.profesor}</td>
                  <td>{row.aula}</td>
                  <td>
                    {new Date(row.fecha + 'T12:00:00').toLocaleDateString('es', {
                      weekday: 'short',
                      day: 'numeric',
                      month: 'short',
                    })}
                  </td>
                  <td>
                    {row.horaInicio} – {row.horaFin}
                  </td>
                  <td>
                    <span className={`admin-badge admin-badge--estado-${row.estado}`}>
                      {estadoLabel[row.estado]}
                    </span>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </>
  )
}

export default Sesiones
