package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.TipoMatriculaRequestDto;
import co.edu.unbosque.horaclass.academy.dto.TipoMatriculaResponseDto;
import co.edu.unbosque.horaclass.academy.service.TipoMatriculaService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-matricula")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TipoMatriculaController {

    private final TipoMatriculaService service;

    public TipoMatriculaController(TipoMatriculaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TipoMatriculaResponseDto> crear(@RequestBody TipoMatriculaRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoMatriculaResponseDto> actualizar(@PathVariable int id,
            @RequestBody TipoMatriculaRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<TipoMatriculaResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoMatriculaResponseDto> obtenerPorId(@PathVariable int id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}