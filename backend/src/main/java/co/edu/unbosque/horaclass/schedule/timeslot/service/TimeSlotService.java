package co.edu.unbosque.horaclass.schedule.timeslot.service;

import co.edu.unbosque.horaclass.schedule.timeslot.dto.TimeSlotRequestDto;
import co.edu.unbosque.horaclass.schedule.timeslot.dto.TimeSlotResponseDto;
import co.edu.unbosque.horaclass.schedule.timeslot.model.TimeSlot;
import co.edu.unbosque.horaclass.schedule.timeslot.repository.TimeSlotRepository;
import co.edu.unbosque.horaclass.schedule.validation.SchedulingRulesValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final SchedulingRulesValidator schedulingRulesValidator;

    public TimeSlotService(TimeSlotRepository timeSlotRepository,
                           SchedulingRulesValidator schedulingRulesValidator) {
        this.timeSlotRepository = timeSlotRepository;
        this.schedulingRulesValidator = schedulingRulesValidator;
    }

    public TimeSlotResponseDto crear(TimeSlotRequestDto request) {
        schedulingRulesValidator.validateTimeSlotRequest(request);
        TimeSlot slot = new TimeSlot();
        slot.setDiaSemana(request.getDiaSemana());
        slot.setHoraInicio(request.getHoraInicio());
        slot.setHoraFin(request.getHoraFin());
        slot.setTurno(request.getTurno());
        slot.setBloqueado(request.getBloqueado() != null ? request.getBloqueado() : false);

        TimeSlot saved = timeSlotRepository.save(slot);
        return toDto(saved);
    }

    public TimeSlotResponseDto actualizar(Long id, TimeSlotRequestDto request) {
        schedulingRulesValidator.validateTimeSlotRequest(request);
        TimeSlot slot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Franja horaria con ID " + id + " no existe"));
        slot.setDiaSemana(request.getDiaSemana());
        slot.setHoraInicio(request.getHoraInicio());
        slot.setHoraFin(request.getHoraFin());
        slot.setTurno(request.getTurno());
        slot.setBloqueado(request.getBloqueado() != null ? request.getBloqueado() : false);

        TimeSlot updated = timeSlotRepository.save(slot);
        return toDto(updated);
    }

    @Transactional(readOnly = true)
    public List<TimeSlotResponseDto> listarTodas() {
        return timeSlotRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TimeSlotResponseDto> listarDisponibles() {
        return timeSlotRepository.findByBloqueadoFalse().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TimeSlotResponseDto obtenerPorId(Long id) {
        TimeSlot slot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Franja horaria con ID " + id + " no existe"));
        return toDto(slot);
    }

    public void eliminar(Long id) {
        if (!timeSlotRepository.existsById(id)) {
            throw new RuntimeException("Franja horaria con ID " + id + " no existe");
        }
        timeSlotRepository.deleteById(id);
    }

    private TimeSlotResponseDto toDto(TimeSlot slot) {
        TimeSlotResponseDto dto = new TimeSlotResponseDto();
        dto.setIdFranja(slot.getIdFranja());
        dto.setDiaSemana(slot.getDiaSemana());
        dto.setHoraInicio(slot.getHoraInicio());
        dto.setHoraFin(slot.getHoraFin());
        dto.setTurno(slot.getTurno());
        dto.setBloqueado(slot.getBloqueado());
        return dto;
    }
}
