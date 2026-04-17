package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.EstadoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EstadoResponseDto;
import co.edu.unbosque.horaclass.academy.service.EstadoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estado")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstadoController {

    private final EstadoService service;

    public EstadoController(EstadoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EstadoResponseDto> crear(@RequestBody EstadoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoResponseDto> actualizar(@PathVariable Integer id,
                                                        @RequestBody EstadoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<EstadoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}