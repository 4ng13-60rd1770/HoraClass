package co.edu.unbosque.horaclass.academy.student.service.impl;

import co.edu.unbosque.horaclass.academy.student.dto.StudentRequestDto;
import co.edu.unbosque.horaclass.academy.student.dto.StudentResponseDto;
import co.edu.unbosque.horaclass.academy.student.model.Student;
import co.edu.unbosque.horaclass.academy.student.repository.StudentRepository;
import co.edu.unbosque.horaclass.academy.student.service.StudentService;
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
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StateRepository stateRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository,
                               UserRepository userRepository,
                               StateRepository stateRepository,
                               PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.stateRepository = stateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public StudentResponseDto crearEstudiante(StudentRequestDto request) {
        if (userRepository.existsByIdUsuario(request.getIdEstudiante())) {
            throw new RuntimeException("Ya existe un usuario con cédula: " + request.getIdEstudiante());
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El username '" + request.getUsername() + "' ya está en uso");
        }

        State estadoActivo = stateRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Estado ACTIVO no encontrado"));

        User user = new User();
        user.setIdUsuario(request.getIdEstudiante());
        user.setPrimerNombre(request.getPrimerNombre());
        user.setPrimerApellido(request.getPrimerApellido());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getIdEstudiante().toString()));
        user.setRol("ESTUDIANTE");
        user.setEstado(estadoActivo);
        User savedUser = userRepository.save(user);

        Student student = new Student();
        student.setUsuario(savedUser);
        student.setCarrera(request.getCarrera());
        student.setSemestre(request.getSemestre());
        student.setCreditos(request.getCreditos());

        return mapToDto(studentRepository.save(student));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDto> listarTodosLosEstudiantes() {
        return studentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDto obtenerEstudiantePorId(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));
        return mapToDto(student);
    }

    @Override
    public StudentResponseDto actualizarEstudiante(Long id, StudentRequestDto request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));
        student.setCarrera(request.getCarrera());
        student.setSemestre(request.getSemestre());
        student.setCreditos(request.getCreditos());
        return mapToDto(studentRepository.save(student));
    }

    @Override
    public void eliminarEstudiante(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Estudiante no encontrado con ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    private StudentResponseDto mapToDto(Student student) {
        StudentResponseDto dto = new StudentResponseDto();
        dto.setIdEstudiante(student.getIdEstudiante());
        User u = student.getUsuario();
        String nombre = u.getPrimerNombre();
        if (u.getPrimerApellido() != null) nombre += " " + u.getPrimerApellido();
        dto.setNombre(nombre);
        dto.setUsername(u.getUsername());
        dto.setCarrera(student.getCarrera());
        dto.setSemestre(student.getSemestre());
        dto.setCreditos(student.getCreditos());
        dto.setEstado(u.getEstado() != null ? u.getEstado().getNombre() : "ACTIVO");
        return dto;
    }
}
