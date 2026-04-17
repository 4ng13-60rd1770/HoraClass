package co.edu.unbosque.horaclass.academy.repository;

import co.edu.unbosque.horaclass.academy.model.Inscripcion;
import co.edu.unbosque.horaclass.academy.model.InscripcionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, InscripcionId> {
}