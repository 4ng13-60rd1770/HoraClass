import {
  mockActividadesRecientes,
  mockDashboardStats,
} from '../../mocks/adminData'
import './admin-pages.css'

const Dashboard = () => {
  const s = mockDashboardStats

  return (
    <>
      <h1 className="admin-page-title">Dashboard</h1>
      <p className="admin-page-desc">
        Resumen general del instituto 
      </p>
      <div className="admin-cards">
        <div className="admin-card">
          <p className="admin-card__label">Sesiones hoy</p>
          <p className="admin-card__value">{s.sesionesHoy}</p>
        </div>
        <div className="admin-card">
          <p className="admin-card__label">Profesores activos</p>
          <p className="admin-card__value">{s.profesoresActivos}</p>
        </div>
        <div className="admin-card">
          <p className="admin-card__label">Estudiantes</p>
          <p className="admin-card__value">{s.estudiantes}</p>
        </div>
        <div className="admin-card">
          <p className="admin-card__label">Aulas en uso</p>
          <p className="admin-card__value">{s.aulasEnUso}</p>
        </div>
      </div>
      <div className="admin-panel admin-panel--mt">
        <h2>Actividad reciente</h2>
        <ul className="admin-activity-list">
          {mockActividadesRecientes.map((a) => (
            <li key={a.id} className="admin-activity-item">
              <span className="admin-activity-item__text">{a.texto}</span>
              <span className="admin-activity-item__time">{a.cuando}</span>
            </li>
          ))}
        </ul>
      </div>
    </>
  )
}

export default Dashboard
