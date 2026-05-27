package co.edu.unbosque.horaclass.schedule.generation.repository;

import co.edu.unbosque.horaclass.schedule.generation.model.ScheduleEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleEntryRepository extends JpaRepository<ScheduleEntry, Long> {
    List<ScheduleEntry> findByScheduleIdHorario(Long idHorario);
}
