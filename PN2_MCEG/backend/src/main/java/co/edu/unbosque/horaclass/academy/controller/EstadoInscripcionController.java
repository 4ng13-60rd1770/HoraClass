package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.EstadoInscripcionRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EstadoInscripcionResponseDto;
import co.edu.unbosque.horaclass.academy.service.EstadoInscripcionService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estado-inscripcion")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstadoInscripcionController {

    private final EstadoInscripcionService service;

    public EstadoInscripcionController(EstadoInscripcionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EstadoInscripcionResponseDto> crear(@RequestBody EstadoInscripcionRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoInscripcionResponseDto> actualizar(@PathVariable Integer id,
                                                                   @RequestBody EstadoInscripcionRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<EstadoInscripcionResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoInscripcionResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}