package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.ProfesorCursoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.ProfesorCursoResponseDto;
import co.edu.unbosque.horaclass.academy.service.ProfesorCursoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesor-curso")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProfesorCursoController {

    private final ProfesorCursoService service;

    public ProfesorCursoController(ProfesorCursoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProfesorCursoResponseDto> crear(@RequestBody ProfesorCursoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    // 🔥 CORREGIDO
    @PutMapping("/{idEmpleado}/{idCurso}")
    public ResponseEntity<ProfesorCursoResponseDto> actualizar(
            @PathVariable int idEmpleado,
            @PathVariable int idCurso,
            @RequestBody ProfesorCursoRequestDto request) {

        return ResponseEntity.ok(service.actualizar(idEmpleado, idCurso, request));
    }

    @GetMapping
    public ResponseEntity<List<ProfesorCursoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{idEmpleado}/{idCurso}")
    public ResponseEntity<ProfesorCursoResponseDto> obtenerPorId(
            @PathVariable int idEmpleado,
            @PathVariable int idCurso) {

        return ResponseEntity.ok(service.obtenerPorId(idEmpleado, idCurso));
    }

    @DeleteMapping("/{idEmpleado}/{idCurso}")
    public ResponseEntity<Void> eliminar(
            @PathVariable int idEmpleado,
            @PathVariable int idCurso) {

        service.eliminar(idEmpleado, idCurso);
        return ResponseEntity.noContent().build();
    }
}