import { mockProfesores } from '../../mocks/adminData'
import './admin-pages.css'

const Profesores = () => {
  return (
    <>
      <h1 className="admin-page-title">Profesores</h1>
      <p className="admin-page-desc">
        Directorio docente en tarjetas (fotos de demostración vía Picsum; sustituye por URLs
        reales desde tu API).
      </p>
      <div className="prof-cards">
        {mockProfesores.map((p) => (
          <article key={p.id} className="prof-card">
            <div className="prof-card__media">
              <img
                src={p.fotoUrl}
                alt={`Foto de ${p.nombres} ${p.apellidos}`}
                className="prof-card__img"
                width={480}
                height={560}
                loading="lazy"
                decoding="async"
              />
              <span
                className={`prof-card__badge ${p.activo ? 'prof-card__badge--activo' : 'prof-card__badge--inactivo'}`}
              >
                {p.activo ? 'Activo' : 'Inactivo'}
              </span>
            </div>
            <div className="prof-card__body">
              <h2 className="prof-card__name">
                {p.nombres} {p.apellidos}
              </h2>
              <p className="prof-card__meta">{p.especialidad}</p>
              <a href={`mailto:${p.email}`} className="prof-card__email">
                {p.email}
              </a>
              <p className="prof-card__hours">
                <span className="prof-card__hours-label">Carga semanal</span>
                <span className="prof-card__hours-value">{p.cargaHorasSemana} h</span>
              </p>
            </div>
          </article>
        ))}
      </div>
    </>
  )
}

export default Profesores
