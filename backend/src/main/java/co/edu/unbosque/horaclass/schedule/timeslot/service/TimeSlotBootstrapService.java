package co.edu.unbosque.horaclass.schedule.timeslot.service;

import co.edu.unbosque.horaclass.schedule.config.SchedulingProperties;
import co.edu.unbosque.horaclass.schedule.timeslot.model.TimeSlot;
import co.edu.unbosque.horaclass.schedule.timeslot.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Genera automáticamente las franjas horarias a partir de las reglas académicas.
 * No requiere registro manual por parte del usuario.
 */
@Service
public class TimeSlotBootstrapService {

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("H:mm");
    private static final String[] DIAS_SEMANA = {
            "LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO"
    };

    private final TimeSlotRepository timeSlotRepository;
    private final SchedulingProperties properties;

    public TimeSlotBootstrapService(TimeSlotRepository timeSlotRepository,
                                    SchedulingProperties properties) {
        this.timeSlotRepository = timeSlotRepository;
        this.properties = properties;
    }

    @Transactional
    public List<TimeSlot> ensureAvailableSlots() {
        List<TimeSlot> existing = timeSlotRepository.findByBloqueadoFalse();
        if (!existing.isEmpty()) {
            return existing;
        }
        List<TimeSlot> generated = buildSlotsFromRules();
        return timeSlotRepository.saveAll(generated);
    }

    private List<TimeSlot> buildSlotsFromRules() {
        List<TimeSlot> slots = new ArrayList<>();
        int durationMinutes = properties.getDuracionSesionHoras() * 60;
        int lunchStart = toMinutes(properties.getHoraInicioAlmuerzo());
        int lunchEnd = toMinutes(properties.getHoraFinAlmuerzo());

        for (String dia : DIAS_SEMANA) {
            boolean sabado = "SABADO".equals(dia);
            int dayStart = toMinutes(sabado ? properties.getHoraInicioSabado() : properties.getHoraInicioSemana());
            int dayEnd = toMinutes(sabado ? properties.getHoraFinSabado() : properties.getHoraFinSemana());

            for (int start = dayStart; start + durationMinutes <= dayEnd; start += durationMinutes) {
                int end = start + durationMinutes;
                if (overlaps(start, end, lunchStart, lunchEnd)) {
                    continue;
                }
                slots.add(buildSlot(dia, start, end));
            }
        }
        return slots;
    }

    private TimeSlot buildSlot(String dia, int startMinutes, int endMinutes) {
        TimeSlot slot = new TimeSlot();
        slot.setDiaSemana(dia);
        slot.setHoraInicio(formatMinutes(startMinutes));
        slot.setHoraFin(formatMinutes(endMinutes));
        slot.setTurno(resolveTurno(startMinutes));
        slot.setBloqueado(false);
        return slot;
    }

    private String resolveTurno(int startMinutes) {
        if (startMinutes < 12 * 60) {
            return "MANANA";
        }
        if (startMinutes < 17 * 60) {
            return "TARDE";
        }
        return "NOCHE";
    }

    private boolean overlaps(int aStart, int aEnd, int bStart, int bEnd) {
        return aStart < bEnd && bStart < aEnd;
    }

    private int toMinutes(String time) {
        LocalTime parsed = LocalTime.parse(time.trim(), TIME_FMT);
        return parsed.getHour() * 60 + parsed.getMinute();
    }

    private String formatMinutes(int minutes) {
        return String.format("%02d:%02d", minutes / 60, minutes % 60);
    }
}
