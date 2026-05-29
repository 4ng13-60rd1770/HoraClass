package co.edu.unbosque.horaclass.schedule.validation;

import co.edu.unbosque.horaclass.academy.classroom.model.Classroom;
import co.edu.unbosque.horaclass.academy.course.model.Course;
import co.edu.unbosque.horaclass.academy.teacher.model.Teacher;
import co.edu.unbosque.horaclass.academy.teacher.repository.TeacherCourseRepository;
import co.edu.unbosque.horaclass.schedule.config.SchedulingProperties;
import co.edu.unbosque.horaclass.schedule.timeslot.dto.TimeSlotRequestDto;
import co.edu.unbosque.horaclass.schedule.timeslot.model.TimeSlot;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class SchedulingRulesValidator {

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("H:mm");

    private final SchedulingProperties properties;
    private final TeacherCourseRepository teacherCourseRepository;

    public SchedulingRulesValidator(SchedulingProperties properties,
                                      TeacherCourseRepository teacherCourseRepository) {
        this.properties = properties;
        this.teacherCourseRepository = teacherCourseRepository;
    }

    public void validateTimeSlotRequest(TimeSlotRequestDto request) {
        if (request.getHoraInicio() == null || request.getHoraFin() == null) {
            throw new IllegalArgumentException("Hora inicio y hora fin son obligatorias.");
        }
        int start = toMinutes(request.getHoraInicio());
        int end = toMinutes(request.getHoraFin());
        if (end <= start) {
            throw new IllegalArgumentException("La hora fin debe ser posterior a la hora inicio.");
        }
        int expected = properties.getDuracionSesionHoras() * 60;
        if (end - start != expected) {
            throw new IllegalArgumentException(
                    "Cada sesión debe durar exactamente " + properties.getDuracionSesionHoras() + " horas.");
        }
        boolean sabado = isSaturday(request.getDiaSemana());
        int dayStart = toMinutes(sabado ? properties.getHoraInicioSabado() : properties.getHoraInicioSemana());
        int dayEnd = toMinutes(sabado ? properties.getHoraFinSabado() : properties.getHoraFinSemana());
        if (start < dayStart || end > dayEnd) {
            throw new IllegalArgumentException("La franja está fuera del horario permitido.");
        }
        boolean bloqueado = Boolean.TRUE.equals(request.getBloqueado());
        if (!bloqueado && overlaps(start, end,
                toMinutes(properties.getHoraInicioAlmuerzo()),
                toMinutes(properties.getHoraFinAlmuerzo()))) {
            throw new IllegalArgumentException("No se programan clases en el bloque de almuerzo.");
        }
    }

    public boolean isSlotValidForScheduling(TimeSlot slot) {
        if (Boolean.TRUE.equals(slot.getBloqueado())) {
            return false;
        }
        TimeSlotRequestDto dto = new TimeSlotRequestDto(
                slot.getDiaSemana(), slot.getHoraInicio(), slot.getHoraFin(), slot.getTurno(), false);
        try {
            validateTimeSlotRequest(dto);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public boolean teacherAcceptsSlot(Teacher teacher, TimeSlot slot) {
        if (teacher.getRestriccionHorario() == null || "SIN_RESTRICCION".equals(teacher.getRestriccionHorario())) {
            return true;
        }
        int start = toMinutes(slot.getHoraInicio());
        int limit16 = toMinutes("16:00");
        int limit18 = toMinutes("18:00");
        return switch (teacher.getRestriccionHorario()) {
            case "SOLO_DIA", "SOLO_MANANA" -> start < limit16;
            case "DESPUES_16" -> start >= limit16;
            case "DESPUES_18" -> start >= limit18;
            default -> true;
        };
    }

    public boolean classroomMeetsCourseRequirements(Classroom classroom, Course course) {
        if (course == null) {
            return true;
        }
        if (Boolean.TRUE.equals(course.getRequiereComputadores())
                && !Boolean.TRUE.equals(classroom.getTieneComputadores())) {
            return false;
        }
        return !Boolean.TRUE.equals(course.getRequiereSillasMoviles())
                || Boolean.TRUE.equals(classroom.getSillasMoviles());
    }

    public void validateGroupCapacity(Integer cupoMinimo, Integer cupoMaximo) {
        if (cupoMaximo == null || cupoMaximo < 1) {
            throw new IllegalArgumentException("El cupo máximo debe ser al menos 1.");
        }
        if (cupoMaximo > properties.getCupoMaximoPermitido()) {
            throw new IllegalArgumentException(
                    "El cupo máximo permitido es " + properties.getCupoMaximoPermitido() + ".");
        }
        if (cupoMinimo != null && cupoMinimo > cupoMaximo) {
            throw new IllegalArgumentException("El cupo mínimo no puede superar el cupo máximo.");
        }
    }

    public void validateTeacherCanTeachCourse(Long idProfesor, Integer idCurso) {
        if (idProfesor == null || idCurso == null) {
            return;
        }
        if (!teacherCourseRepository.existsByIdProfesorAndIdCurso(idProfesor, idCurso)) {
            throw new IllegalArgumentException("El docente no está habilitado para orientar este curso.");
        }
    }

    public void validateCourseSessions(int sesiones) {
        if (sesiones < properties.getSesionesSemanalesMin()) {
            throw new IllegalArgumentException(
                    "El curso debe tener al menos " + properties.getSesionesSemanalesMin() + " sesión(es) semanal(es).");
        }
        if (sesiones > properties.getSesionesSemanalesMax()) {
            throw new IllegalArgumentException(
                    "El curso no puede superar " + properties.getSesionesSemanalesMax() + " sesiones semanales.");
        }
    }

    public void validateClassroomCapacity(Integer capacidad) {
        if (capacidad == null || capacidad < 1) {
            throw new IllegalArgumentException("La capacidad del aula es obligatoria.");
        }
        if (capacidad < properties.getGrupoCupoMinimo()) {
            throw new IllegalArgumentException(
                    "La capacidad mínima de un aula es " + properties.getGrupoCupoMinimo() + " asientos.");
        }
        if (capacidad > properties.getGrupoCupoBase()) {
            throw new IllegalArgumentException(
                    "La capacidad máxima de un aula es " + properties.getGrupoCupoBase() + " asientos.");
        }
    }

    public int resolveCargaHoras(String tipoVinculacion, Integer cargaSolicitada) {
        int max = switch (tipoVinculacion != null ? tipoVinculacion : "") {
            case "TIEMPO_COMPLETO" -> properties.getCargaHorasTiempoCompleto();
            case "TRES_CUARTOS" -> properties.getCargaHorasTresCuartos();
            case "MEDIO_TIEMPO" -> properties.getCargaHorasMedioTiempo();
            case "CUARTO_TIEMPO" -> properties.getCargaHorasCuartoTiempo();
            default -> properties.getCargaHorasTiempoCompleto();
        };
        if (cargaSolicitada == null || cargaSolicitada <= 0) {
            return max;
        }
        if (cargaSolicitada > max) {
            throw new IllegalArgumentException("La carga horaria no puede superar " + max + " h/semana.");
        }
        return cargaSolicitada;
    }

    private boolean isSaturday(String diaSemana) {
        return diaSemana != null && "SABADO".equalsIgnoreCase(diaSemana);
    }

    private boolean overlaps(int aStart, int aEnd, int bStart, int bEnd) {
        return aStart < bEnd && bStart < aEnd;
    }

    private int toMinutes(String time) {
        try {
            LocalTime parsed = LocalTime.parse(time.trim(), TIME_FMT);
            return parsed.getHour() * 60 + parsed.getMinute();
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Formato de hora inválido: " + time);
        }
    }
}
