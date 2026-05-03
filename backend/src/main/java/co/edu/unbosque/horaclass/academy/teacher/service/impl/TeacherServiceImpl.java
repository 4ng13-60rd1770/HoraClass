package co.edu.unbosque.horaclass.academy.teacher.service.impl;

import co.edu.unbosque.horaclass.academy.teacher.dto.TeacherRequestDto;
import co.edu.unbosque.horaclass.academy.teacher.dto.TeacherResponseDto;
import co.edu.unbosque.horaclass.academy.teacher.model.Teacher;
import co.edu.unbosque.horaclass.academy.teacher.repository.TeacherRepository;
import co.edu.unbosque.horaclass.academy.teacher.service.TeacherService;
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
    private final UserRepository userRepository;
    private final StateRepository stateRepository;
    private final PasswordEncoder passwordEncoder;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                               UserRepository userRepository,
                               StateRepository stateRepository,
                               PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.stateRepository = stateRepository;
        this.passwordEncoder = passwordEncoder;
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
        teacher.setCargaHoras(request.getCargaHoras() != null ? request.getCargaHoras() : 0);

        return mapToDto(teacherRepository.save(teacher));
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
        if (request.getCargaHoras() != null) teacher.setCargaHoras(request.getCargaHoras());
        return mapToDto(teacherRepository.save(teacher));
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
        return dto;
    }
}
