package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.GrupoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.GrupoResponseDto;
import co.edu.unbosque.horaclass.academy.service.GrupoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupo")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GrupoController {

    private final GrupoService service;

    public GrupoController(GrupoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GrupoResponseDto> crear(@RequestBody GrupoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoResponseDto> actualizar(@PathVariable Integer id,
                                                       @RequestBody GrupoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<GrupoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}