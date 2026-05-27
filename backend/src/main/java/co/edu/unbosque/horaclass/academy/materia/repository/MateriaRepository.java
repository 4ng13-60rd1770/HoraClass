package co.edu.unbosque.horaclass.academy.materia.repository;

import co.edu.unbosque.horaclass.academy.materia.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    List<Materia> findByCarrera(String carrera);
    List<Materia> findByDocente(String docente);
}
