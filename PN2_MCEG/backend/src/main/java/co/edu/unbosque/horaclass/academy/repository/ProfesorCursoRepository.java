package co.edu.unbosque.horaclass.academy.repository;

import co.edu.unbosque.horaclass.academy.model.ProfesorCurso;
import co.edu.unbosque.horaclass.academy.model.ProfesorCursoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorCursoRepository extends JpaRepository<ProfesorCurso, ProfesorCursoId> {
}