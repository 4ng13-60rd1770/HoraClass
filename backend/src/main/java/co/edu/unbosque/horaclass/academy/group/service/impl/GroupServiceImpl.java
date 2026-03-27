package co.edu.unbosque.horaclass.academy.group.service.impl;

import co.edu.unbosque.horaclass.academy.course.dto.CourseRespondeDto;
import co.edu.unbosque.horaclass.academy.course.model.Course;
import co.edu.unbosque.horaclass.academy.course.repositiry.CourseRepository;
import co.edu.unbosque.horaclass.academy.group.dto.GroupRequestDto;
import co.edu.unbosque.horaclass.academy.group.dto.GroupResponseDto;
import co.edu.unbosque.horaclass.academy.group.dto.GroupStateResponseDto;
import co.edu.unbosque.horaclass.academy.group.model.Group;
import co.edu.unbosque.horaclass.academy.group.model.GroupState;
import co.edu.unbosque.horaclass.academy.group.repository.GroupRepository;
import co.edu.unbosque.horaclass.academy.group.repository.GroupStateRepository;
import co.edu.unbosque.horaclass.academy.group.service.GroupService;
import co.edu.unbosque.horaclass.academy.teacher.dto.TeacherResponseDto;
import co.edu.unbosque.horaclass.academy.teacher.model.Teacher;
import co.edu.unbosque.horaclass.academy.teacher.repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final GroupStateRepository groupStateRepository;
    private final TeacherRepository teacherRepository;
    ModelMapper mapper;

    public GroupServiceImpl(GroupRepository groupRepository,
                            CourseRepository courseRepository,
                            GroupStateRepository groupStateRepository,
                            TeacherRepository teacherRepository,
                            ModelMapper mapper) {
        this.groupRepository = groupRepository;
        this.courseRepository = courseRepository;
        this.groupStateRepository = groupStateRepository;
        this.teacherRepository = teacherRepository;
        this.mapper = mapper;
    }

    private void validacionRepos(GroupRequestDto request){
        if (!courseRepository.existsById(request.getIdCurso())) {
            throw new RuntimeException("El curso con ID " + request.getIdCurso() + " no existe");
        }
        if (!teacherRepository.existsById(request.getIdProfesor())) {
            throw new RuntimeException("El profesor con ID " + request.getIdProfesor() + " no existe");
        }
        if (!groupStateRepository.existsById(request.getIdEstadoGrupo())) {
            throw new RuntimeException("El estado de grupo con ID " + request.getIdEstadoGrupo() + " no existe");
        }

    }
    @Override
    public GroupResponseDto crearGrupo(GroupRequestDto request) {

        validacionRepos(request);
        // VALIDACIÓN 4: Cupo mínimo >= 10
        if (request.getCupoMinimo() < 10) {
            throw new RuntimeException(
                    "El cupo mínimo debe ser al menos 10 estudiantes. Valor recibido: " +
                            request.getCupoMinimo());
        }
        // VALIDACIÓN 5: Cupo máximo <= 40 + 10% tolerancia (44)
        int cupoMaximoPermitido = 44; // 40 + 10%
        if (request.getCupoMaximo() > cupoMaximoPermitido) {
            throw new RuntimeException(
                    "El cupo máximo no puede superar " + cupoMaximoPermitido +
                            " estudiantes (40 + 10% tolerancia). Valor recibido: " +
                            request.getCupoMaximo());
        }
        // VALIDACIÓN 6: Cupo mínimo <= Cupo máximo
        if (request.getCupoMinimo() > request.getCupoMaximo()) {
            throw new RuntimeException(
                    "El cupo mínimo (" + request.getCupoMinimo() +
                            ") no puede ser mayor que el cupo máximo (" + request.getCupoMaximo() + ")");
        }

        // VALIDACIÓN 7: Grupo no duplicado (mismo curso + profesor)
        if (groupRepository.existsByCursoIdCursoAndProfesorIdProfesor(
                request.getIdCurso(), request.getIdProfesor())) {
            throw new RuntimeException(
                    "Ya existe un grupo con ese curso y profesor asignado");
        }

        // TODO: VALIDACIÓN 8: Profesor puede dictar ese curso (PRO_CUR)
        // Esta validación requiere la tabla PRO_CUR
        /*
        if (!proCurRepository.existsByIdEmpleadoAndIdCurso(
                request.getIdProfesor(), request.getIdCurso())) {
            throw new RuntimeException(
                "El profesor no está habilitado para dictar este curso");
        }
        */

        Course curso = courseRepository.findById(request.getIdCurso()).get();
        Teacher profesor = teacherRepository.findById(request.getIdProfesor()).get();
        GroupState estadoGrupo = groupStateRepository.findById(request.getIdEstadoGrupo()).get();
        //Forzar Lazy load
        curso.getIdModalidad().getNombre();
        curso.getIdTipoCurso().getNombre();
        profesor.getUsuario().getUsername();


        Group grupo = new Group();
        grupo.setIdGrupo(request.getIdGrupo());
        grupo.setCurso(curso);
        grupo.setProfesor(profesor);
        grupo.setCupoMaximo(request.getCupoMaximo());
        grupo.setCupoMinimo(request.getCupoMinimo());
        grupo.setEstadoGrupo(estadoGrupo);


        Group grupoGuardado = groupRepository.save(grupo);
        GroupResponseDto res = mapper.map(grupoGuardado, GroupResponseDto.class);
        return res;
    }

    @Override
    public GroupResponseDto actualizarGrupo(GroupRequestDto request) {
        Group grupo = groupRepository.findById(request.getIdGrupo())
                .orElseThrow(() -> new RuntimeException(
                        "El grupo con ID " + request.getIdGrupo() + " no existe"));
        Course curso = courseRepository.findById(request.getIdCurso())
                .orElseThrow(() -> new RuntimeException(
                        "El curso con ID " + request.getIdCurso() + " no existe"));
        Teacher profesor = teacherRepository.findById(request.getIdProfesor())
                .orElseThrow(() -> new RuntimeException(
                        "El profesor con ID " + request.getIdProfesor() + " no existe"));
        GroupState estadoGrupo = groupStateRepository.findById(request.getIdEstadoGrupo())
                .orElseThrow(() -> new RuntimeException(
                        "El estado de grupo con ID " + request.getIdEstadoGrupo() + " no existe"));
        if (request.getCupoMinimo() < 10) {
            throw new RuntimeException("El cupo mínimo debe ser al menos 10");
        }
        if (request.getCupoMaximo() > 44) {
            throw new RuntimeException("El cupo máximo no puede superar 44");
        }
        if (request.getCupoMinimo() > request.getCupoMaximo()) {
            throw new RuntimeException("El cupo mínimo no puede ser mayor al máximo");
        }
        grupo.setCurso(curso);
        grupo.setProfesor(profesor);
        grupo.setCupoMaximo(request.getCupoMaximo());
        grupo.setCupoMinimo(request.getCupoMinimo());
        grupo.setEstadoGrupo(estadoGrupo);

        Group grupoActualizado = groupRepository.save(grupo);

        return mapper.map(grupoActualizado, GroupResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupResponseDto> listarTodosLosGrupos() {
        return groupRepository.findAll().stream()
                .map(grupo -> mapper.map(grupo, GroupResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupResponseDto> listarGruposPorCurso(Integer idCurso) {
        return List.of();
    }

    @Override
    public List<GroupResponseDto> listarGruposPorProfesor(Long idProfesor) {
        return List.of();
    }

    @Override
    public GroupResponseDto obtenerGrupoPorId(String idGrupo) {
        Group grupo = groupRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException(
                        "El grupo con ID " + idGrupo + " no existe"));
        return mapper.map(grupo, GroupResponseDto.class);

    }

    @Override
    public void eliminarGrupo(String idGrupo) {
        if (!groupRepository.existsById(idGrupo)) {
            throw new RuntimeException(
                    "El grupo con ID " + idGrupo + " no existe");
        }
        groupRepository.deleteById(idGrupo);
    }
}
