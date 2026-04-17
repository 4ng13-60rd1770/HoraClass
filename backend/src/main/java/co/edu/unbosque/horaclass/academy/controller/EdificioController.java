package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.EdificioRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EdificioResponseDto;
import co.edu.unbosque.horaclass.academy.service.EdificioService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/edificio")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EdificioController {

    private final EdificioService service;

    public EdificioController(EdificioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EdificioResponseDto> crear(@RequestBody EdificioRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EdificioResponseDto> actualizar(@PathVariable Integer id,
                                                          @RequestBody EdificioRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<EdificioResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EdificioResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}