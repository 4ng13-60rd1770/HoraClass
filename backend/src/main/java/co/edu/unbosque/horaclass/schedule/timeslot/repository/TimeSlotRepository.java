package co.edu.unbosque.horaclass.schedule.timeslot.repository;

import co.edu.unbosque.horaclass.schedule.timeslot.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByBloqueadoFalse();
    List<TimeSlot> findByTurno(String turno);
    List<TimeSlot> findByDiaSemana(String diaSemana);
}
