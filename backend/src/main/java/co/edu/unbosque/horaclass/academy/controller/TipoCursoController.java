package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.TipoCursoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.TipoCursoResponseDto;
import co.edu.unbosque.horaclass.academy.service.TipoCursoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-curso")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TipoCursoController {

    private final TipoCursoService service;

    public TipoCursoController(TipoCursoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TipoCursoResponseDto> crear(@RequestBody TipoCursoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCursoResponseDto> actualizar(@PathVariable int id,
            @RequestBody TipoCursoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<TipoCursoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCursoResponseDto> obtenerPorId(@PathVariable int id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}