package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.SesionRequestDto;
import co.edu.unbosque.horaclass.academy.dto.SesionResponseDto;
import co.edu.unbosque.horaclass.academy.service.SesionService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sesion")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SesionController {

    private final SesionService service;

    public SesionController(SesionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SesionResponseDto> crear(@RequestBody SesionRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SesionResponseDto> actualizar(@PathVariable int id,
            @RequestBody SesionRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<SesionResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SesionResponseDto> obtenerPorId(@PathVariable int id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}