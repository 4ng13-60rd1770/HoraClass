package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.ModalidadCursoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.ModalidadCursoResponseDto;
import co.edu.unbosque.horaclass.academy.service.ModalidadCursoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modalidad-curso")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModalidadCursoController {

    private final ModalidadCursoService service;

    public ModalidadCursoController(ModalidadCursoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ModalidadCursoResponseDto> crear(@RequestBody ModalidadCursoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModalidadCursoResponseDto> actualizar(@PathVariable Integer id,
                                                                @RequestBody ModalidadCursoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<ModalidadCursoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModalidadCursoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}