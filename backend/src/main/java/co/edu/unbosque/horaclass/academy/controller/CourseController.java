package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.CourseRequestDto;
import co.edu.unbosque.horaclass.academy.dto.CourseRespondeDto;
import co.edu.unbosque.horaclass.academy.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/cursos/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseRespondeDto> crearCurso(@RequestBody CourseRequestDto request) {
        CourseRespondeDto curso = courseService.crearCurso(request);
        return new ResponseEntity<>(curso, HttpStatus.CREATED);
    }


    @PutMapping("/cursos/{idCurso}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseRespondeDto> actualizarCurso(
            @PathVariable Integer idCurso,
            @RequestBody CourseRequestDto request) {
        CourseRespondeDto curso = courseService.actualizarCurso(idCurso, request);
        return ResponseEntity.ok(curso);
    }

    @GetMapping("/cursos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CourseRespondeDto>> listarTodosLosCursos() {
        List<CourseRespondeDto> cursos = courseService.listarTodosLosCursos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/cursos/{idCurso}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseRespondeDto> obtenerCursoPorId(@PathVariable Integer idCurso) {
        CourseRespondeDto curso = courseService.obtenerCursoPorId(idCurso);
        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/cursos/{idCurso}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Integer idCurso) {
        courseService.eliminarCurso(idCurso);
        return ResponseEntity.noContent().build();
    }
}
