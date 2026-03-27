import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import AdminLayout from './layouts/AdminLayout'
import Login from './pages/Login'
import Calendario from './pages/admin/Calendario'
import Dashboard from './pages/admin/Dashboard'
import Estudiantes from './pages/admin/Estudiantes'
import Grupos from './pages/admin/Grupos'
import Profesores from './pages/admin/Profesores'
import Sesiones from './pages/admin/Sesiones'

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/admin" element={<AdminLayout />}>
          <Route index element={<Navigate to="dashboard" replace />} />
          <Route path="dashboard" element={<Dashboard />} />
          <Route path="calendario" element={<Calendario />} />
          <Route path="sesiones" element={<Sesiones />} />
          <Route path="profesores" element={<Profesores />} />
          <Route path="estudiantes" element={<Estudiantes />} />
          <Route path="grupos" element={<Grupos />} />
        </Route>
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
