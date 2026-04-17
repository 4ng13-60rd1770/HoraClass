package co.edu.unbosque.horaclass.academy.repository;

import co.edu.unbosque.horaclass.academy.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    Optional<Curso> findCursoByNombre(String nombre);
    boolean existsCursoByNombre(String nombre);
    boolean existsCursoByIdCurso(Integer idCurso);
}


