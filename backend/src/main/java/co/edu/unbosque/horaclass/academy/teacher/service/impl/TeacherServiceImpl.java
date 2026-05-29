package co.edu.unbosque.horaclass.academy.teacher.service.impl;

import co.edu.unbosque.horaclass.academy.teacher.dto.TeacherRequestDto;
import co.edu.unbosque.horaclass.academy.teacher.dto.TeacherResponseDto;
import co.edu.unbosque.horaclass.academy.teacher.model.Teacher;
import co.edu.unbosque.horaclass.academy.teacher.model.TeacherCourse;
import co.edu.unbosque.horaclass.academy.teacher.repository.TeacherCourseRepository;
import co.edu.unbosque.horaclass.academy.teacher.repository.TeacherRepository;
import co.edu.unbosque.horaclass.academy.teacher.service.TeacherService;
import co.edu.unbosque.horaclass.schedule.validation.SchedulingRulesValidator;
import co.edu.unbosque.horaclass.user.model.State;
import co.edu.unbosque.horaclass.user.model.User;
import co.edu.unbosque.horaclass.user.repository.StateRepository;
import co.edu.unbosque.horaclass.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherCourseRepository teacherCourseRepository;
    private final UserRepository userRepository;
    private final StateRepository stateRepository;
    private final PasswordEncoder passwordEncoder;
    private final SchedulingRulesValidator schedulingRulesValidator;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                               TeacherCourseRepository teacherCourseRepository,
                               UserRepository userRepository,
                               StateRepository stateRepository,
                               PasswordEncoder passwordEncoder,
                               SchedulingRulesValidator schedulingRulesValidator) {
        this.teacherRepository = teacherRepository;
        this.teacherCourseRepository = teacherCourseRepository;
        this.userRepository = userRepository;
        this.stateRepository = stateRepository;
        this.passwordEncoder = passwordEncoder;
        this.schedulingRulesValidator = schedulingRulesValidator;
    }

    @Override
    public TeacherResponseDto crearProfesor(TeacherRequestDto request) {
        if (userRepository.existsByIdUsuario(request.getIdProfesor())) {
            throw new RuntimeException("Ya existe un usuario con cédula: " + request.getIdProfesor());
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El username '" + request.getUsername() + "' ya está en uso");
        }

        State estadoActivo = stateRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Estado ACTIVO no encontrado"));

        User user = new User();
        user.setIdUsuario(request.getIdProfesor());
        user.setPrimerNombre(request.getPrimerNombre());
        user.setPrimerApellido(request.getPrimerApellido());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getIdProfesor().toString()));
        user.setRol("DOCENTE");
        user.setEstado(estadoActivo);
        User savedUser = userRepository.save(user);

        Teacher teacher = new Teacher();
        teacher.setUsuario(savedUser);
        teacher.setDepartamento(request.getCarrera());
        teacher.setEspecialidad(request.getCarrera());
        teacher.setEscalafon(request.getEscalafon());
        teacher.setTipoVinculacion(request.getTipoVinculacion());
        teacher.setRestriccionHorario(request.getRestriccionHorario());
        teacher.setCargaHoras(schedulingRulesValidator.resolveCargaHoras(
                request.getTipoVinculacion(), request.getCargaHoras()));

        Teacher saved = teacherRepository.save(teacher);
        syncTeacherCourses(saved.getIdProfesor(), request.getCursosHabilitados());
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherResponseDto> listarTodosLosProfesores() {
        return teacherRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherResponseDto obtenerProfesorPorId(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + id));
        return mapToDto(teacher);
    }

    @Override
    public TeacherResponseDto actualizarProfesor(Long id, TeacherRequestDto request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + id));
        teacher.setDepartamento(request.getCarrera());
        teacher.setEspecialidad(request.getCarrera());
        if (request.getEscalafon() != null) teacher.setEscalafon(request.getEscalafon());
        if (request.getTipoVinculacion() != null) teacher.setTipoVinculacion(request.getTipoVinculacion());
        if (request.getRestriccionHorario() != null) teacher.setRestriccionHorario(request.getRestriccionHorario());
        teacher.setCargaHoras(schedulingRulesValidator.resolveCargaHoras(
                request.getTipoVinculacion() != null ? request.getTipoVinculacion() : teacher.getTipoVinculacion(),
                request.getCargaHoras()));
        Teacher saved = teacherRepository.save(teacher);
        if (request.getCursosHabilitados() != null) {
            syncTeacherCourses(saved.getIdProfesor(), request.getCursosHabilitados());
        }
        return mapToDto(saved);
    }

    @Override
    public void eliminarProfesor(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + id));
        Long userId = teacher.getIdProfesor();
        teacherRepository.deleteById(id);
        userRepository.deleteById(String.valueOf(userId));
    }

    private TeacherResponseDto mapToDto(Teacher teacher) {
        TeacherResponseDto dto = new TeacherResponseDto();
        dto.setIdProfesor(teacher.getIdProfesor());
        String nombre = teacher.getUsuario().getPrimerNombre();
        if (teacher.getUsuario().getPrimerApellido() != null) {
            nombre += " " + teacher.getUsuario().getPrimerApellido();
        }
        dto.setNombre(nombre);
        dto.setUsername(teacher.getUsuario().getUsername());
        dto.setDepartamento(teacher.getDepartamento());
        dto.setEspecialidad(teacher.getEspecialidad());
        dto.setCargaHoras(teacher.getCargaHoras());
        dto.setEscalafon(teacher.getEscalafon());
        dto.setTipoVinculacion(teacher.getTipoVinculacion());
        dto.setRestriccionHorario(teacher.getRestriccionHorario());
        dto.setCursosHabilitados(teacherCourseRepository.findByIdProfesor(teacher.getIdProfesor())
                .stream()
                .map(TeacherCourse::getIdCurso)
                .toList());
        return dto;
    }

    private void syncTeacherCourses(Long idProfesor, List<Integer> cursos) {
        if (cursos == null) {
            return;
        }
        teacherCourseRepository.deleteByIdProfesor(idProfesor);
        for (Integer idCurso : cursos) {
            teacherCourseRepository.save(new TeacherCourse(idProfesor, idCurso));
        }
    }
}
