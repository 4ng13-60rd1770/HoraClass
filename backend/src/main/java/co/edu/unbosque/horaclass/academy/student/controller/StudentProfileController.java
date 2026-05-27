package co.edu.unbosque.horaclass.academy.student.controller;

import co.edu.unbosque.horaclass.academy.student.dto.StudentResponseDto;
import co.edu.unbosque.horaclass.academy.student.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/estudiante")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentProfileController {

    private final StudentService studentService;

    public StudentProfileController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Devuelve el perfil del estudiante autenticado.
     * Accesible con ROLE_ESTUDIANTE usando su propio token JWT.
     */
    @GetMapping("/perfil")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<StudentResponseDto> obtenerPerfil(Principal principal) {
        return ResponseEntity.ok(studentService.obtenerEstudiantePorUsername(principal.getName()));
    }
}
