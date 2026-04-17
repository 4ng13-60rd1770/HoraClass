package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.PregradoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.PregradoResponseDto;
import co.edu.unbosque.horaclass.academy.service.PregradoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pregrado")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PregradoController {

    private final PregradoService service;

    public PregradoController(PregradoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PregradoResponseDto> crear(@RequestBody PregradoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PregradoResponseDto> actualizar(@PathVariable int id,
            @RequestBody PregradoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<PregradoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PregradoResponseDto> obtenerPorId(@PathVariable int id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}