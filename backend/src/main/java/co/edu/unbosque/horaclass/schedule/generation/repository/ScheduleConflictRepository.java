package co.edu.unbosque.horaclass.schedule.generation.repository;

import co.edu.unbosque.horaclass.schedule.generation.model.ScheduleConflict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleConflictRepository extends JpaRepository<ScheduleConflict, Long> {
    List<ScheduleConflict> findByScheduleIdHorario(Long idHorario);
    long countByScheduleIdHorarioAndEstado(Long idHorario, String estado);
}
