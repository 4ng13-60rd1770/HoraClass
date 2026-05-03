package co.edu.unbosque.horaclass.academy.student.controller;

import co.edu.unbosque.horaclass.academy.student.dto.StudentRequestDto;
import co.edu.unbosque.horaclass.academy.student.dto.StudentResponseDto;
import co.edu.unbosque.horaclass.academy.student.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/estudiantes/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDto> crearEstudiante(@RequestBody StudentRequestDto request) {
        return new ResponseEntity<>(studentService.crearEstudiante(request), HttpStatus.CREATED);
    }

    @GetMapping("/estudiantes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StudentResponseDto>> listarEstudiantes() {
        return ResponseEntity.ok(studentService.listarTodosLosEstudiantes());
    }

    @GetMapping("/estudiantes/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDto> obtenerEstudiante(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.obtenerEstudiantePorId(id));
    }

    @PutMapping("/estudiantes/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDto> actualizarEstudiante(
            @PathVariable Long id,
            @RequestBody StudentRequestDto request) {
        return ResponseEntity.ok(studentService.actualizarEstudiante(id, request));
    }

    @DeleteMapping("/estudiantes/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) {
        studentService.eliminarEstudiante(id);
        return ResponseEntity.noContent().build();
    }
}
