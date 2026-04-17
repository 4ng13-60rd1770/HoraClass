package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.EstadoCursoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EstadoCursoResponseDto;
import co.edu.unbosque.horaclass.academy.service.EstadoCursoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estado-curso")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstadoCursoController {

    private final EstadoCursoService service;

    public EstadoCursoController(EstadoCursoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EstadoCursoResponseDto> crear(@RequestBody EstadoCursoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoCursoResponseDto> actualizar(@PathVariable Integer id,
                                                             @RequestBody EstadoCursoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<EstadoCursoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoCursoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}