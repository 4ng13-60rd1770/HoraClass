package co.edu.unbosque.horaclass.schedule.generation.engine;

import co.edu.unbosque.horaclass.academy.classroom.model.Classroom;
import co.edu.unbosque.horaclass.academy.group.model.Group;
import co.edu.unbosque.horaclass.academy.teacher.model.Teacher;
import co.edu.unbosque.horaclass.academy.teacher.repository.TeacherCourseRepository;
import co.edu.unbosque.horaclass.schedule.config.SchedulingProperties;
import co.edu.unbosque.horaclass.schedule.generation.model.ConflictReason;
import co.edu.unbosque.horaclass.schedule.generation.model.Schedule;
import co.edu.unbosque.horaclass.schedule.generation.model.ScheduleConflict;
import co.edu.unbosque.horaclass.schedule.generation.model.ScheduleEntry;
import co.edu.unbosque.horaclass.schedule.timeslot.model.TimeSlot;
import co.edu.unbosque.horaclass.schedule.validation.SchedulingRulesValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Motor de generación de horarios usando algoritmo Greedy con Backtracking.
 * Adaptado de GreedyBacktrackingStrategy del Proyecto_Nasly.
 *
 * Mejoras sobre Nasly:
 * - Asignación automática de aulas (Nasly no asignaba aulas)
 * - Validación de capacidad de aula vs cupo del grupo
 * - Sin cruce de aula en la misma franja
 * - Restricciones simplificadas directamente en código (sin tabla Restriccion ni Observer)
 */
@Component
public class ScheduleGenerator {

    private final SchedulingRulesValidator schedulingRulesValidator;
    private final SchedulingProperties schedulingProperties;
    private final TeacherCourseRepository teacherCourseRepository;

    public ScheduleGenerator(SchedulingRulesValidator schedulingRulesValidator,
                             SchedulingProperties schedulingProperties,
                             TeacherCourseRepository teacherCourseRepository) {
        this.schedulingRulesValidator = schedulingRulesValidator;
        this.schedulingProperties = schedulingProperties;
        this.teacherCourseRepository = teacherCourseRepository;
    }

    /**
     * Genera un horario académico asignando para cada grupo:
     * una franja horaria, un docente y un aula.
     */
    public Schedule generar(List<Group> grupos,
                            List<Teacher> docentes,
                            List<TimeSlot> franjas,
                            List<Classroom> aulas) {
        return generarVariantes(grupos, docentes, franjas, aulas, 1);
    }

    /**
     * Genera una o varias variantes de horario y elige la mejor según el menor número de conflictos.
     */
    public Schedule generarVariantes(List<Group> grupos,
                                     List<Teacher> docentes,
                                     List<TimeSlot> franjas,
                                     List<Classroom> aulas,
                                     int variantes) {
        if (variantes <= 1) {
            return generarInterno(new ArrayList<>(grupos), new ArrayList<>(docentes), franjas, aulas);
        }

        Schedule mejorHorario = null;
        int mejorConflictos = Integer.MAX_VALUE;

        for (int intento = 0; intento < variantes; intento++) {
            List<Group> gruposIntento = new ArrayList<>(grupos);
            List<Teacher> docentesIntento = new ArrayList<>(docentes);

            if (intento > 0) {
                Collections.shuffle(gruposIntento, new Random(intento + 1));
                Collections.shuffle(docentesIntento, new Random(intento + 7));
            }

            Schedule horarioIntento = generarInterno(gruposIntento, docentesIntento, franjas, aulas);
            int conflictos = horarioIntento.getConflictos().size();
            if (mejorHorario == null || conflictos < mejorConflictos) {
                mejorHorario = horarioIntento;
                mejorConflictos = conflictos;
                if (mejorConflictos == 0) {
                    break;
                }
            }
        }

        return mejorHorario;
    }

    private Schedule generarInterno(List<Group> grupos,
                                    List<Teacher> docentes,
                                    List<TimeSlot> franjas,
                                    List<Classroom> aulas) {

        Schedule schedule = new Schedule();
        schedule.setFechaGeneracion(LocalDate.now());
        schedule.setEstado("BORRADOR");
        schedule.setPublicado(false);

        // Mapa para rastrear asignaciones en memoria (evitar cruces)
        // clave: franjaId -> set de docenteIds asignados a esa franja
        Map<Long, Set<Long>> docentePorFranja = new HashMap<>();
        // clave: franjaId -> set de aulaIds asignados a esa franja
        Map<Long, Set<Integer>> aulaPorFranja = new HashMap<>();
        // carga actual de horas por docente (idProfesor -> horasAsignadas)
        Map<Long, Integer> cargaActualDocente = new HashMap<>();

        // Inicializar carga actual de docentes en 0
        for (Teacher d : docentes) {
            cargaActualDocente.put(d.getIdProfesor(), 0);
        }

        // Mapa: grupoId -> docente asignado en la primera sesión
        Map<String, Teacher> docentePorGrupo = new HashMap<>();

        List<TimeSlot> franjasValidas = franjas.stream()
                .filter(schedulingRulesValidator::isSlotValidForScheduling)
                .collect(Collectors.toList());

        // Ordenar grupos por cupo máximo descendente (los más grandes primero, más difíciles de asignar)
        grupos.sort(Comparator.comparingInt(Group::getCupoMaximo).reversed());

        for (Group grupo : grupos) {
            int sesionesRequeridas = obtenerSesionesSemanales(grupo);
            Teacher docenteGrupo = grupo.getProfesor();

            for (int sesion = 0; sesion < sesionesRequeridas; sesion++) {
                List<Teacher> docentesCandidatos = resolverDocentesCandidatos(
                        grupo, docentes, docenteGrupo, docentePorGrupo);

                AssignmentResult resultado = intentarAsignar(
                        grupo, docentesCandidatos, franjasValidas, aulas,
                        docentePorFranja, aulaPorFranja, cargaActualDocente);

                if (resultado.isExitoso()) {
                    ScheduleEntry entry = new ScheduleEntry();
                    entry.setGrupo(grupo);
                    entry.setFranja(resultado.getFranja());
                    entry.setDocente(resultado.getDocente());
                    entry.setAula(resultado.getAula());
                    entry.setEstado("ACTIVO");

                    schedule.agregarEntry(entry);
                    docentePorGrupo.putIfAbsent(grupo.getIdGrupo(), resultado.getDocente());

                    Long franjaId = resultado.getFranja().getIdFranja();
                    docentePorFranja.computeIfAbsent(franjaId, k -> new HashSet<>())
                            .add(resultado.getDocente().getIdProfesor());
                    aulaPorFranja.computeIfAbsent(franjaId, k -> new HashSet<>())
                            .add(resultado.getAula().getIdSalon());

                    int horasSesion = schedulingProperties.getDuracionSesionHoras();
                    cargaActualDocente.merge(resultado.getDocente().getIdProfesor(),
                            horasSesion, Integer::sum);

                } else {
                    ScheduleConflict conflicto = new ScheduleConflict();
                    conflicto.setGrupo(grupo);
                    conflicto.setMotivo(resultado.getMotivo());
                    conflicto.setDescripcion(resultado.getDescripcion()
                            + " (sesión " + (sesion + 1) + " de " + sesionesRequeridas + ")");
                    conflicto.setEstado("PENDIENTE");
                    conflicto.setFechaDeteccion(LocalDateTime.now());
                    schedule.agregarConflicto(conflicto);
                }
            }
        }

        detectarConflictosDeValidacion(schedule);

        return schedule;
    }

    private int obtenerSesionesSemanales(Group grupo) {
        if (grupo.getCurso() != null && grupo.getCurso().getSesionesSemanales() != null) {
            return grupo.getCurso().getSesionesSemanales();
        }
        return schedulingProperties.getSesionesSemanalesMin();
    }

    private List<Teacher> resolverDocentesCandidatos(Group grupo,
                                                      List<Teacher> docentes,
                                                      Teacher docenteGrupo,
                                                      Map<String, Teacher> docentePorGrupo) {
        if (docentePorGrupo.containsKey(grupo.getIdGrupo())) {
            return List.of(docentePorGrupo.get(grupo.getIdGrupo()));
        }
        if (docenteGrupo != null) {
            return List.of(docenteGrupo);
        }
        return docentes;
    }

    private void detectarConflictosDeValidacion(Schedule schedule) {
        Map<String, ScheduleEntry> docentePorFranja = new HashMap<>();
        Map<String, ScheduleEntry> aulaPorFranja = new HashMap<>();

        for (ScheduleEntry entry : schedule.getEntries()) {
            if (entry.getFranja() == null || entry.getDocente() == null || entry.getAula() == null) {
                continue;
            }

            String docenteKey = entry.getFranja().getIdFranja() + "-doc-" + entry.getDocente().getIdProfesor();
            if (docentePorFranja.containsKey(docenteKey)) {
                ScheduleConflict conflict = new ScheduleConflict();
                conflict.setGrupo(entry.getGrupo());
                conflict.setMotivo(ConflictReason.DOCENTE_DUPLICADO);
                String nombreDocente = entry.getDocente().getUsuario() != null
                        ? entry.getDocente().getUsuario().getPrimerNombre() + " " + entry.getDocente().getUsuario().getPrimerApellido()
                        : String.valueOf(entry.getDocente().getIdProfesor());
                conflict.setDescripcion("El docente " + nombreDocente + " está asignado en dos grupos a la misma franja.");
                conflict.setEstado("PENDIENTE");
                conflict.setFechaDeteccion(LocalDateTime.now());
                schedule.agregarConflicto(conflict);
            } else {
                docentePorFranja.put(docenteKey, entry);
            }

            String aulaKey = entry.getFranja().getIdFranja() + "-aula-" + entry.getAula().getIdSalon();
            if (aulaPorFranja.containsKey(aulaKey)) {
                ScheduleConflict conflict = new ScheduleConflict();
                conflict.setGrupo(entry.getGrupo());
                conflict.setMotivo(ConflictReason.AULA_DUPLICADA);
                conflict.setDescripcion("El aula " + entry.getAula().getNombre() + " está ocupada por más de un grupo en la misma franja.");
                conflict.setEstado("PENDIENTE");
                conflict.setFechaDeteccion(LocalDateTime.now());
                schedule.agregarConflicto(conflict);
            } else {
                aulaPorFranja.put(aulaKey, entry);
            }
        }
    }

    /**
     * Intenta asignar un grupo a la mejor combinación de docente + franja + aula.
     * Primero intenta con docentes de la misma especialidad, luego con todos.
     */
    private AssignmentResult intentarAsignar(Group grupo,
                                              List<Teacher> docentes,
                                              List<TimeSlot> franjas,
                                              List<Classroom> aulas,
                                              Map<Long, Set<Long>> docentePorFranja,
                                              Map<Long, Set<Integer>> aulaPorFranja,
                                              Map<Long, Integer> cargaActualDocente) {

        String especialidadCurso = grupo.getCurso() != null
                ? grupo.getCurso().getIdTipoCurso() != null
                    ? grupo.getCurso().getIdTipoCurso().getNombre()
                    : null
                : null;

        // Paso 1: Intentar con docentes de la misma especialidad
        List<Teacher> docentesEspecialidad = docentes.stream()
                .filter(d -> d.getEspecialidad() != null &&
                        d.getEspecialidad().equalsIgnoreCase(
                                especialidadCurso != null ? especialidadCurso : ""))
                .collect(Collectors.toList());

        if (!docentesEspecialidad.isEmpty()) {
            AssignmentResult resultado = buscarMejorAsignacion(
                    grupo, docentesEspecialidad, franjas, aulas,
                    docentePorFranja, aulaPorFranja, cargaActualDocente, false);
            if (resultado.isExitoso()) return resultado;
        }

        // Paso 2: Intentar con todos los docentes
        AssignmentResult resultado = buscarMejorAsignacion(
                grupo, docentes, franjas, aulas,
                docentePorFranja, aulaPorFranja, cargaActualDocente, true);
        if (resultado.isExitoso()) return resultado;

        // Paso 3: Diagnosticar motivo del fallo
        return diagnosticarMotivo(grupo, docentes, franjas, aulas,
                docentePorFranja, aulaPorFranja, cargaActualDocente);
    }

    /**
     * Busca la mejor combinación de franja + docente + aula para un grupo.
     * Usa un sistema de puntaje para priorizar mejores asignaciones.
     */
    private AssignmentResult buscarMejorAsignacion(Group grupo,
                                                    List<Teacher> docentes,
                                                    List<TimeSlot> franjas,
                                                    List<Classroom> aulas,
                                                    Map<Long, Set<Long>> docentePorFranja,
                                                    Map<Long, Set<Integer>> aulaPorFranja,
                                                    Map<Long, Integer> cargaActualDocente,
                                                    boolean fueraEspecialidad) {
        Teacher mejorDocente = null;
        TimeSlot mejorFranja = null;
        Classroom mejorAula = null;
        int mejorPuntaje = -1;

        for (TimeSlot franja : franjas) {
            if (!schedulingRulesValidator.isSlotValidForScheduling(franja)) continue;

            for (Teacher docente : docentes) {
                if (grupo.getCurso() != null
                        && !teacherCourseRepository.existsByIdProfesorAndIdCurso(
                                docente.getIdProfesor(), grupo.getCurso().getIdCurso())) {
                    continue;
                }
                if (!schedulingRulesValidator.teacherAcceptsSlot(docente, franja)) continue;

                Set<Long> docentesEnFranja = docentePorFranja.getOrDefault(
                        franja.getIdFranja(), Collections.emptySet());
                if (docentesEnFranja.contains(docente.getIdProfesor())) continue;

                int horasSesion = schedulingProperties.getDuracionSesionHoras();
                int cargaActual = cargaActualDocente.getOrDefault(docente.getIdProfesor(), 0);
                if (cargaActual + horasSesion > docente.getCargaHoras()) continue;

                Classroom aulaDisponible = buscarAulaDisponible(
                        grupo, franja, aulas, aulaPorFranja);
                if (aulaDisponible == null) continue;

                // ======== RESTRICCIONES BLANDAS (puntaje) ========
                int puntaje = 0;

                // Bonus por especialidad del docente coincide con tipo de curso
                String especialidadCurso = grupo.getCurso() != null
                        && grupo.getCurso().getIdTipoCurso() != null
                        ? grupo.getCurso().getIdTipoCurso().getNombre()
                        : null;
                if (docente.getEspecialidad() != null && especialidadCurso != null
                        && docente.getEspecialidad().equalsIgnoreCase(especialidadCurso)) {
                    puntaje += 10;
                }

                // Bonus por menor carga actual (preferir docentes con menos carga)
                puntaje += Math.max(0, docente.getCargaHoras() - cargaActual);

                // Bonus por aula con capacidad más ajustada (evitar desperdiciar aulas grandes)
                int diferenciaCapacidad = aulaDisponible.getCapacidad() - grupo.getCupoMaximo();
                puntaje += Math.max(0, 10 - diferenciaCapacidad);

                if (puntaje > mejorPuntaje) {
                    mejorPuntaje = puntaje;
                    mejorDocente = docente;
                    mejorFranja = franja;
                    mejorAula = aulaDisponible;
                }
            }
        }

        if (mejorDocente != null) {
            AssignmentResult r = AssignmentResult.exito(mejorDocente, mejorFranja, mejorAula);
            r.setFueraEspecialidad(fueraEspecialidad);
            return r;
        }

        return AssignmentResult.sinResultado();
    }

    /**
     * Busca un aula disponible para la franja dada que tenga capacidad suficiente.
     */
    private Classroom buscarAulaDisponible(Group grupo, TimeSlot franja,
                                            List<Classroom> aulas,
                                            Map<Long, Set<Integer>> aulaPorFranja) {
        Set<Integer> aulasOcupadas = aulaPorFranja.getOrDefault(
                franja.getIdFranja(), Collections.emptySet());

        return aulas.stream()
                .filter(a -> a.getDisponible())
                .filter(a -> !aulasOcupadas.contains(a.getIdSalon()))
                .filter(a -> a.getCapacidad() >= grupo.getCupoMaximo())
                .filter(a -> schedulingRulesValidator.classroomMeetsCourseRequirements(a, grupo.getCurso()))
                .min(Comparator.comparingInt(Classroom::getCapacidad))
                .orElse(null);
    }

    /**
     * Diagnostica el motivo por el cual no se pudo asignar un grupo.
     */
    private AssignmentResult diagnosticarMotivo(Group grupo,
                                                 List<Teacher> docentes,
                                                 List<TimeSlot> franjas,
                                                 List<Classroom> aulas,
                                                 Map<Long, Set<Long>> docentePorFranja,
                                                 Map<Long, Set<Integer>> aulaPorFranja,
                                                 Map<Long, Integer> cargaActualDocente) {
        if (docentes.isEmpty()) {
            return AssignmentResult.fallo(
                    ConflictReason.SIN_DOCENTE_DISPONIBLE,
                    "No hay docentes registrados en el sistema para asignar al grupo " +
                            grupo.getIdGrupo());
        }

        List<TimeSlot> franjasDisponibles = franjas.stream()
                .filter(f -> !f.getBloqueado())
                .collect(Collectors.toList());
        if (franjasDisponibles.isEmpty()) {
            return AssignmentResult.fallo(
                    ConflictReason.SIN_FRANJA_DISPONIBLE,
                    "No hay franjas horarias disponibles para el grupo " +
                            grupo.getIdGrupo());
        }

        // Verificar si todas las aulas con capacidad están ocupadas
        boolean hayAulaConCapacidad = aulas.stream()
                .anyMatch(a -> a.getDisponible() && a.getCapacidad() >= grupo.getCupoMaximo());
        if (!hayAulaConCapacidad) {
            return AssignmentResult.fallo(
                    ConflictReason.SIN_AULA_CON_CAPACIDAD,
                    "No hay aulas con capacidad suficiente (" + grupo.getCupoMaximo() +
                            " estudiantes) para el grupo " + grupo.getIdGrupo());
        }

        // Verificar si todos los docentes alcanzaron su carga máxima
        boolean todosEnCargaMaxima = docentes.stream().allMatch(d -> {
            int horasSesion = schedulingProperties.getDuracionSesionHoras();
            int carga = cargaActualDocente.getOrDefault(d.getIdProfesor(), 0);
            return carga + horasSesion > d.getCargaHoras();
        });
        if (todosEnCargaMaxima) {
            return AssignmentResult.fallo(
                    ConflictReason.CARGA_MAXIMA_ALCANZADA,
                    "Todos los docentes han alcanzado su carga máxima de horas para el grupo " +
                            grupo.getIdGrupo());
        }

        return AssignmentResult.fallo(
                ConflictReason.SIN_AULA_DISPONIBLE,
                "No se encontró combinación válida de docente + franja + aula para el grupo " +
                        grupo.getIdGrupo());
    }
}
