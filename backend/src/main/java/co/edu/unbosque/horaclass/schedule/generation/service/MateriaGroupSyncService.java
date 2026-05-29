package co.edu.unbosque.horaclass.schedule.generation.service;

import co.edu.unbosque.horaclass.academy.course.model.Course;
import co.edu.unbosque.horaclass.academy.course.model.CourseType;
import co.edu.unbosque.horaclass.academy.course.model.Mode;
import co.edu.unbosque.horaclass.academy.course.repositiry.CourseRepository;
import co.edu.unbosque.horaclass.academy.course.repositiry.CourseTypeRepository;
import co.edu.unbosque.horaclass.academy.course.repositiry.ModeRepository;
import co.edu.unbosque.horaclass.academy.group.model.Group;
import co.edu.unbosque.horaclass.academy.group.model.GroupState;
import co.edu.unbosque.horaclass.academy.group.repository.GroupRepository;
import co.edu.unbosque.horaclass.academy.group.repository.GroupStateRepository;
import co.edu.unbosque.horaclass.academy.materia.model.Materia;
import co.edu.unbosque.horaclass.academy.materia.repository.MateriaRepository;
import co.edu.unbosque.horaclass.academy.teacher.model.Teacher;
import co.edu.unbosque.horaclass.academy.teacher.model.TeacherCourse;
import co.edu.unbosque.horaclass.academy.teacher.repository.TeacherCourseRepository;
import co.edu.unbosque.horaclass.academy.teacher.repository.TeacherRepository;
import co.edu.unbosque.horaclass.schedule.config.SchedulingProperties;
import co.edu.unbosque.horaclass.user.model.User;
import co.edu.unbosque.horaclass.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Convierte las materias del dashboard en grupos académicos listos para generar horarios.
 */
@Service
public class MateriaGroupSyncService {

    private static final int CURSO_ID_OFFSET = 900_000;

    private final MateriaRepository materiaRepository;
    private final GroupRepository groupRepository;
    private final GroupStateRepository groupStateRepository;
    private final CourseRepository courseRepository;
    private final ModeRepository modeRepository;
    private final CourseTypeRepository courseTypeRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherCourseRepository teacherCourseRepository;
    private final UserRepository userRepository;
    private final SchedulingProperties schedulingProperties;

    public MateriaGroupSyncService(MateriaRepository materiaRepository,
                                   GroupRepository groupRepository,
                                   GroupStateRepository groupStateRepository,
                                   CourseRepository courseRepository,
                                   ModeRepository modeRepository,
                                   CourseTypeRepository courseTypeRepository,
                                   TeacherRepository teacherRepository,
                                   TeacherCourseRepository teacherCourseRepository,
                                   UserRepository userRepository,
                                   SchedulingProperties schedulingProperties) {
        this.materiaRepository = materiaRepository;
        this.groupRepository = groupRepository;
        this.groupStateRepository = groupStateRepository;
        this.courseRepository = courseRepository;
        this.modeRepository = modeRepository;
        this.courseTypeRepository = courseTypeRepository;
        this.teacherRepository = teacherRepository;
        this.teacherCourseRepository = teacherCourseRepository;
        this.userRepository = userRepository;
        this.schedulingProperties = schedulingProperties;
    }

    @Transactional
    public List<Group> syncFromMaterias() {
        List<Materia> materias = materiaRepository.findAll();
        if (materias.isEmpty()) {
            return List.of();
        }

        GroupState estadoActivo = ensureGroupState(1, "ACTIVO");
        ensureGroupState(2, "ABIERTO");
        Mode modalidad = ensureMode();
        CourseType tipoCurso = ensureCourseType();
        List<Teacher> todosDocentes = teacherRepository.findAll();

        List<Group> grupos = new ArrayList<>();
        for (Materia materia : materias) {
            int idCurso = CURSO_ID_OFFSET + materia.getId().intValue();
            Course curso = courseRepository.findById(idCurso).orElseGet(Course::new);
            curso.setIdCurso(idCurso);
            curso.setNombre(materia.getNombre());
            curso.setSemestre(1);
            curso.setCreditos(3);
            curso.setIdModalidad(modalidad);
            curso.setIdTipoCurso(tipoCurso);
            curso.setSesionesSemanales(countSesiones(materia.getHorario()));
            curso.setRequiereComputadores(false);
            curso.setRequiereSillasMoviles(false);
            courseRepository.save(curso);

            Teacher profesor = resolveTeacher(materia, todosDocentes);
            linkTeachersToCourse(curso.getIdCurso(), profesor, todosDocentes);

            String idGrupo = "M-" + materia.getId();
            Group grupo = groupRepository.findById(idGrupo).orElseGet(Group::new);
            grupo.setIdGrupo(idGrupo);
            grupo.setCurso(curso);
            grupo.setProfesor(profesor);
            grupo.setCupoMaximo(materia.getCupoMax() != null ? materia.getCupoMax() : schedulingProperties.getGrupoCupoBase());
            grupo.setCupoMinimo(Math.min(schedulingProperties.getGrupoCupoMinimo(), grupo.getCupoMaximo()));
            grupo.setEstadoGrupo(estadoActivo);
            grupos.add(groupRepository.save(grupo));
        }
        return grupos;
    }

    private Teacher resolveTeacher(Materia materia, List<Teacher> todosDocentes) {
        if (materia.getDocente() != null && !materia.getDocente().isBlank()) {
            Optional<User> user = userRepository.findUserByUsername(materia.getDocente().trim());
            if (user.isPresent()) {
                Optional<Teacher> byUser = teacherRepository.findByUsuarioIdUsuario(user.get().getIdUsuario());
                if (byUser.isPresent()) {
                    return byUser.get();
                }
            }
        }
        if (materia.getCarrera() != null) {
            Optional<Teacher> sameDept = todosDocentes.stream()
                    .filter(t -> materia.getCarrera().equalsIgnoreCase(t.getDepartamento()))
                    .findFirst();
            if (sameDept.isPresent()) {
                return sameDept.get();
            }
        }
        return todosDocentes.isEmpty() ? null : todosDocentes.get(0);
    }

    private void linkTeachersToCourse(int idCurso, Teacher preferido, List<Teacher> todosDocentes) {
        if (preferido != null) {
            ensureTeacherCourse(preferido.getIdProfesor(), idCurso);
            return;
        }
        for (Teacher docente : todosDocentes) {
            ensureTeacherCourse(docente.getIdProfesor(), idCurso);
        }
    }

    private void ensureTeacherCourse(Long idProfesor, int idCurso) {
        if (!teacherCourseRepository.existsByIdProfesorAndIdCurso(idProfesor, idCurso)) {
            teacherCourseRepository.save(new TeacherCourse(idProfesor, idCurso));
        }
    }

    private int countSesiones(String horario) {
        if (horario == null || horario.isBlank()) {
            return schedulingProperties.getSesionesSemanalesMin();
        }
        String h = horario.toLowerCase(Locale.ROOT)
                .replace("miércoles", "miercoles")
                .replace("sábado", "sabado");
        int count = 0;
        String[] dias = {"lunes", "martes", "miercoles", "jueves", "viernes", "sabado"};
        for (String dia : dias) {
            if (h.contains(dia)) {
                count++;
            }
        }
        if (count == 0) {
            return schedulingProperties.getSesionesSemanalesMin();
        }
        return Math.min(count, schedulingProperties.getSesionesSemanalesMax());
    }

    private GroupState ensureGroupState(int id, String nombre) {
        return groupStateRepository.findById(id)
                .orElseGet(() -> groupStateRepository.save(new GroupState(id, nombre)));
    }

    private Mode ensureMode() {
        return modeRepository.findById(1)
                .orElseGet(() -> modeRepository.save(new Mode(1, "Presencial")));
    }

    private CourseType ensureCourseType() {
        return courseTypeRepository.findById(1)
                .orElseGet(() -> courseTypeRepository.save(new CourseType(1, "Obligatoria")));
    }
}
