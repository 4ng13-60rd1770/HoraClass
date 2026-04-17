package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.RestriccionProfesorRequestDto;
import co.edu.unbosque.horaclass.academy.dto.RestriccionProfesorResponseDto;
import co.edu.unbosque.horaclass.academy.service.RestriccionProfesorService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restriccion-profesor")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RestriccionProfesorController {

    private final RestriccionProfesorService service;

    public RestriccionProfesorController(RestriccionProfesorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RestriccionProfesorResponseDto> crear(@RequestBody RestriccionProfesorRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestriccionProfesorResponseDto> actualizar(@PathVariable int id,
            @RequestBody RestriccionProfesorRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<RestriccionProfesorResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestriccionProfesorResponseDto> obtenerPorId(@PathVariable int id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}