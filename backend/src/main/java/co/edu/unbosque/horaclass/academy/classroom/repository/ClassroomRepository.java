package co.edu.unbosque.horaclass.academy.classroom.repository;

import co.edu.unbosque.horaclass.academy.classroom.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    List<Classroom> findByDisponible(Boolean disponible);
    long countByDisponibleTrue();
}
