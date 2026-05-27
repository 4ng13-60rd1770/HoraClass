package co.edu.unbosque.horaclass.schedule.generation.repository;

import co.edu.unbosque.horaclass.schedule.generation.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findBySemestre(String semestre);
    boolean existsBySemestre(String semestre);
}
