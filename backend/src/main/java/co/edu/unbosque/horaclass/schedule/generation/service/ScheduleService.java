package co.edu.unbosque.horaclass.schedule.generation.service;

import co.edu.unbosque.horaclass.academy.classroom.model.Classroom;
import co.edu.unbosque.horaclass.academy.classroom.repository.ClassroomRepository;
import co.edu.unbosque.horaclass.academy.group.model.Group;
import co.edu.unbosque.horaclass.academy.group.repository.GroupRepository;
import co.edu.unbosque.horaclass.academy.teacher.model.Teacher;
import co.edu.unbosque.horaclass.academy.teacher.repository.TeacherRepository;
import co.edu.unbosque.horaclass.schedule.generation.dto.ScheduleConflictResponseDto;
import co.edu.unbosque.horaclass.schedule.generation.dto.ScheduleEntryResponseDto;
import co.edu.unbosque.horaclass.schedule.generation.dto.ScheduleResponseDto;
import co.edu.unbosque.horaclass.schedule.generation.engine.ScheduleGenerator;
import co.edu.unbosque.horaclass.schedule.generation.model.Schedule;
import co.edu.unbosque.horaclass.schedule.generation.model.ScheduleConflict;
import co.edu.unbosque.horaclass.schedule.generation.model.ScheduleEntry;
import co.edu.unbosque.horaclass.schedule.generation.repository.ScheduleConflictRepository;
import co.edu.unbosque.horaclass.schedule.generation.repository.ScheduleEntryRepository;
import co.edu.unbosque.horaclass.schedule.generation.repository.ScheduleRepository;
import co.edu.unbosque.horaclass.schedule.timeslot.model.TimeSlot;
import co.edu.unbosque.horaclass.schedule.timeslot.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que orquesta la generación de horarios académicos.
 * Adaptado de HorarioService del Proyecto_Nasly.
 */
@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleEntryRepository entryRepository;
    private final ScheduleConflictRepository conflictRepository;
    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ClassroomRepository classroomRepository;
    private final ScheduleGenerator generator;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           ScheduleEntryRepository entryRepository,
                           ScheduleConflictRepository conflictRepository,
                           GroupRepository groupRepository,
                           TeacherRepository teacherRepository,
                           TimeSlotRepository timeSlotRepository,
                           ClassroomRepository classroomRepository,
                           ScheduleGenerator generator) {
        this.scheduleRepository = scheduleRepository;
        this.entryRepository = entryRepository;
        this.conflictRepository = conflictRepository;
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.classroomRepository = classroomRepository;
        this.generator = generator;
    }

    /**
     * Genera un horario académico para el semestre dado.
     */
    public ScheduleResponseDto generarHorario(String semestre) {
        // Validar que no exista un horario previo
        if (scheduleRepository.existsBySemestre(semestre)) {
            throw new RuntimeException(
                    "Ya existe un horario generado para el semestre " + semestre +
                    ". Elimínelo primero si desea regenerar.");
        }

        // Cargar datos necesarios
        List<Group> grupos = groupRepository.findByEstadoGrupoNombre("ACTIVO");
        if (grupos.isEmpty()) {
            grupos = groupRepository.findByEstadoGrupoNombre("ABIERTO");
        }
        if (grupos.isEmpty()) {
            throw new RuntimeException(
                    "No hay grupos activos registrados. Cree grupos antes de generar el horario.");
        }

        List<Teacher> docentes = teacherRepository.findAll();
        if (docentes.isEmpty()) {
            throw new RuntimeException(
                    "No hay docentes registrados en el sistema.");
        }

        List<TimeSlot> franjas = timeSlotRepository.findByBloqueadoFalse();
        if (franjas.isEmpty()) {
            throw new RuntimeException(
                    "No hay franjas horarias disponibles. Registre franjas horarias primero.");
        }

        List<Classroom> aulas = classroomRepository.findByDisponible(true);
        if (aulas.isEmpty()) {
            throw new RuntimeException(
                    "No hay aulas disponibles registradas en el sistema.");
        }

        // Ejecutar el algoritmo de generación
        Schedule schedule = generator.generar(grupos, docentes, franjas, aulas);
        schedule.setSemestre(semestre);

        // Persistir el horario
        Schedule saved = scheduleRepository.save(schedule);

        // Persistir entries
        for (ScheduleEntry entry : schedule.getEntries()) {
            entry.setSchedule(saved);
            entryRepository.save(entry);
        }

        // Persistir conflictos
        for (ScheduleConflict conflict : schedule.getConflictos()) {
            conflict.setSchedule(saved);
            conflictRepository.save(conflict);
        }

        return toResponseDto(saved);
    }

    /**
     * Obtiene el horario de un semestre.
     */
    @Transactional(readOnly = true)
    public ScheduleResponseDto obtenerPorSemestre(String semestre) {
        Schedule schedule = scheduleRepository.findBySemestre(semestre)
                .orElseThrow(() -> new RuntimeException(
                        "No existe horario para el semestre " + semestre));

        // Cargar entries y conflictos
        List<ScheduleEntry> entries = entryRepository.findByScheduleIdHorario(schedule.getIdHorario());
        List<ScheduleConflict> conflictos = conflictRepository.findByScheduleIdHorario(schedule.getIdHorario());

        schedule.setEntries(entries);
        schedule.setConflictos(conflictos);

        return toResponseDto(schedule);
    }

    /**
     * Publica un horario (cambia estado a PUBLICADO).
     */
    public ScheduleResponseDto publicar(String semestre) {
        Schedule schedule = scheduleRepository.findBySemestre(semestre)
                .orElseThrow(() -> new RuntimeException(
                        "No existe horario para el semestre " + semestre));

        long pendientes = conflictRepository.countByScheduleIdHorarioAndEstado(
                schedule.getIdHorario(), "PENDIENTE");

        if (pendientes > 0) {
            throw new RuntimeException(
                    "No se puede publicar el horario. Hay " + pendientes +
                    " conflictos pendientes de resolver.");
        }

        schedule.setEstado("PUBLICADO");
        schedule.setPublicado(true);
        Schedule updated = scheduleRepository.save(schedule);

        List<ScheduleEntry> entries = entryRepository.findByScheduleIdHorario(updated.getIdHorario());
        updated.setEntries(entries);
        updated.setConflictos(List.of());

        return toResponseDto(updated);
    }

    /**
     * Elimina un horario y sus entries/conflictos para permitir regeneración.
     */
    public void eliminar(String semestre) {
        Schedule schedule = scheduleRepository.findBySemestre(semestre)
                .orElseThrow(() -> new RuntimeException(
                        "No existe horario para el semestre " + semestre));
        scheduleRepository.delete(schedule);
    }

    /**
     * Lista todos los horarios generados.
     */
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> listarTodos() {
        return scheduleRepository.findAll().stream()
                .map(schedule -> {
                    ScheduleResponseDto dto = new ScheduleResponseDto();
                    dto.setIdHorario(schedule.getIdHorario());
                    dto.setSemestre(schedule.getSemestre());
                    dto.setEstado(schedule.getEstado());
                    dto.setFechaGeneracion(schedule.getFechaGeneracion());
                    dto.setPublicado(schedule.getPublicado());
                    long entryCount = entryRepository.findByScheduleIdHorario(schedule.getIdHorario()).size();
                    long conflictCount = conflictRepository.findByScheduleIdHorario(schedule.getIdHorario()).size();
                    dto.setTotalEntries((int) entryCount);
                    dto.setTotalConflictos((int) conflictCount);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // ========== Mapeo a DTOs ==========

    private ScheduleResponseDto toResponseDto(Schedule schedule) {
        ScheduleResponseDto dto = new ScheduleResponseDto();
        dto.setIdHorario(schedule.getIdHorario());
        dto.setSemestre(schedule.getSemestre());
        dto.setEstado(schedule.getEstado());
        dto.setFechaGeneracion(schedule.getFechaGeneracion());
        dto.setPublicado(schedule.getPublicado());

        List<ScheduleEntryResponseDto> entryDtos = schedule.getEntries().stream()
                .map(this::toEntryDto)
                .collect(Collectors.toList());
        dto.setEntries(entryDtos);
        dto.setTotalEntries(entryDtos.size());

        List<ScheduleConflictResponseDto> conflictDtos = schedule.getConflictos().stream()
                .map(this::toConflictDto)
                .collect(Collectors.toList());
        dto.setConflictos(conflictDtos);
        dto.setTotalConflictos(conflictDtos.size());

        return dto;
    }

    private ScheduleEntryResponseDto toEntryDto(ScheduleEntry entry) {
        ScheduleEntryResponseDto dto = new ScheduleEntryResponseDto();
        dto.setIdEntrada(entry.getIdEntrada());
        dto.setEstado(entry.getEstado());

        if (entry.getGrupo() != null) {
            dto.setIdGrupo(entry.getGrupo().getIdGrupo());
            if (entry.getGrupo().getCurso() != null) {
                dto.setNombreCurso(entry.getGrupo().getCurso().getNombre());
                dto.setSemestreCurso(entry.getGrupo().getCurso().getSemestre());
                dto.setCreditosCurso(entry.getGrupo().getCurso().getCreditos());
            }
        }

        if (entry.getDocente() != null) {
            String nombreDocente = "";
            if (entry.getDocente().getUsuario() != null) {
                nombreDocente = entry.getDocente().getUsuario().getPrimerNombre() + " " +
                        entry.getDocente().getUsuario().getPrimerApellido();
            }
            dto.setNombreDocente(nombreDocente);
            dto.setEspecialidadDocente(entry.getDocente().getEspecialidad());
        }

        if (entry.getFranja() != null) {
            dto.setDiaSemana(entry.getFranja().getDiaSemana());
            dto.setHoraInicio(entry.getFranja().getHoraInicio());
            dto.setHoraFin(entry.getFranja().getHoraFin());
        }

        if (entry.getAula() != null) {
            dto.setNombreAula(entry.getAula().getNombre());
            dto.setEdificioAula(entry.getAula().getEdificio());
            dto.setCapacidadAula(entry.getAula().getCapacidad());
        }

        return dto;
    }

    private ScheduleConflictResponseDto toConflictDto(ScheduleConflict conflict) {
        ScheduleConflictResponseDto dto = new ScheduleConflictResponseDto();
        dto.setIdConflicto(conflict.getIdConflicto());
        dto.setMotivo(conflict.getMotivo() != null ? conflict.getMotivo().name() : "DESCONOCIDO");
        dto.setDescripcion(conflict.getDescripcion());
        dto.setEstado(conflict.getEstado());
        dto.setFechaDeteccion(conflict.getFechaDeteccion());

        if (conflict.getGrupo() != null) {
            dto.setIdGrupo(conflict.getGrupo().getIdGrupo());
            if (conflict.getGrupo().getCurso() != null) {
                dto.setNombreCurso(conflict.getGrupo().getCurso().getNombre());
            }
        }

        return dto;
    }
}
