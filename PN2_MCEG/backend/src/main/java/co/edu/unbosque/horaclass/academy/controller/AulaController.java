package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.AulaRequestDto;
import co.edu.unbosque.horaclass.academy.dto.AulaResponseDto;
import co.edu.unbosque.horaclass.academy.service.AulaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aula")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AulaController {

    private final AulaService service;

    public AulaController(AulaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AulaResponseDto> crear(@RequestBody AulaRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AulaResponseDto> actualizar(@PathVariable Integer id,
                                                      @RequestBody AulaRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<AulaResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}