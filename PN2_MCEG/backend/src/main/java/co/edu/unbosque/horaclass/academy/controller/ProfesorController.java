package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.ProfesorRequestDto;
import co.edu.unbosque.horaclass.academy.dto.ProfesorResponseDto;
import co.edu.unbosque.horaclass.academy.service.ProfesorService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesor")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProfesorController {

    private final ProfesorService service;

    public ProfesorController(ProfesorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProfesorResponseDto> crear(@RequestBody ProfesorRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfesorResponseDto> actualizar(@PathVariable int id,
            @RequestBody ProfesorRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<ProfesorResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorResponseDto> obtenerPorId(@PathVariable int id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}