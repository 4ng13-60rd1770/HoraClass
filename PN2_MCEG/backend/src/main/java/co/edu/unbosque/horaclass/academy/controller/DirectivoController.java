package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.DirectivoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.DirectivoResponseDto;
import co.edu.unbosque.horaclass.academy.service.DirectivoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directivo")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DirectivoController {

    private final DirectivoService service;

    public DirectivoController(DirectivoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DirectivoResponseDto> crear(@RequestBody DirectivoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectivoResponseDto> actualizar(@PathVariable Integer id,
                                                           @RequestBody DirectivoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<DirectivoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectivoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}