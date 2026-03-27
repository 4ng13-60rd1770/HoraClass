import { mockEstudiantes } from '../../mocks/adminData'
import './admin-pages.css'

const Estudiantes = () => {
  return (
    <>
      <h1 className="admin-page-title">Estudiantes</h1>
      <p className="admin-page-desc">
      </p>
      <div className="admin-table-wrap">
        <table className="admin-table">
          <thead>
            <tr>
              <th>Documento</th>
              <th>Nombre</th>
              <th>Programa</th>
              <th>Grupo</th>
              <th>Sem.</th>
            </tr>
          </thead>
          <tbody>
            {[...mockEstudiantes]
              .sort((a, b) => a.apellidos.localeCompare(b.apellidos))
              .map((e) => (
                <tr key={e.id}>
                  <td className="admin-table__mono">{e.documento}</td>
                  <td>
                    {e.nombres} {e.apellidos}
                  </td>
                  <td>{e.programa}</td>
                  <td>{e.grupo}</td>
                  <td>{e.semestre}</td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </>
  )
}

export default Estudiantes
