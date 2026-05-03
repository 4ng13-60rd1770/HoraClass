package co.edu.unbosque.horaclass.academy.teacher.controller;

import co.edu.unbosque.horaclass.academy.teacher.dto.TeacherRequestDto;
import co.edu.unbosque.horaclass.academy.teacher.dto.TeacherResponseDto;
import co.edu.unbosque.horaclass.academy.teacher.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/profesores")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherResponseDto> crearProfesor(@RequestBody TeacherRequestDto request) {
        return new ResponseEntity<>(teacherService.crearProfesor(request), HttpStatus.CREATED);
    }

    @GetMapping("/profesores")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TeacherResponseDto>> listarProfesores() {
        return ResponseEntity.ok(teacherService.listarTodosLosProfesores());
    }

    @GetMapping("/profesores/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherResponseDto> obtenerProfesor(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.obtenerProfesorPorId(id));
    }

    @PutMapping("/profesores/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherResponseDto> actualizarProfesor(@PathVariable Long id, @RequestBody TeacherRequestDto request) {
        return ResponseEntity.ok(teacherService.actualizarProfesor(id, request));
    }

    @DeleteMapping("/profesores/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarProfesor(@PathVariable Long id) {
        teacherService.eliminarProfesor(id);
        return ResponseEntity.noContent().build();
    }
}
