package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.DiaRequestDto;
import co.edu.unbosque.horaclass.academy.dto.DiaResponseDto;
import co.edu.unbosque.horaclass.academy.service.DiaService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dia")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DiaController {

    private final DiaService service;

    public DiaController(DiaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DiaResponseDto> crear(@RequestBody DiaRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiaResponseDto> actualizar(@PathVariable Integer id,
                                                     @RequestBody DiaRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<DiaResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}