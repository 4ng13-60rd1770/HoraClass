import { NavLink, Outlet, useNavigate } from 'react-router-dom'
import { clearSession } from '../pages/authStorage'
import './AdminLayout.css'

const nav = [
  { to: '/admin/dashboard', label: 'Dashboard' },
  { to: '/admin/calendario', label: 'Calendario' },
  { to: '/admin/sesiones', label: 'Sesiones' },
  { to: '/admin/profesores', label: 'Profesores' },
  { to: '/admin/grupos', label: 'Grupos' },
  { to: '/admin/estudiantes', label: 'Estudiantes' },
] as const

const AdminLayout = () => {
  const navigate = useNavigate()

  const salir = () => {
    clearSession()
    navigate('/login', { replace: true })
  }

  return (
    <div className="admin-shell">
      <aside className="admin-sidebar">
        <div className="admin-brand">HoraClass</div>
        <p className="admin-role">Panel administrador</p>
        <nav className="admin-nav">
          {nav.map((item) => (
            <NavLink
              key={item.to}
              to={item.to}
              className={({ isActive }) =>
                `admin-nav__link${isActive ? ' admin-nav__link--active' : ''}`
              }
            >
              {item.label}
            </NavLink>
          ))}
        </nav>
        <button type="button" className="admin-logout" onClick={salir}>
          Cerrar sesión
        </button>
      </aside>
      <div className="admin-main-wrap">
        <main className="admin-main">
          <Outlet />
        </main>
      </div>
    </div>
  )
}

export default AdminLayout
