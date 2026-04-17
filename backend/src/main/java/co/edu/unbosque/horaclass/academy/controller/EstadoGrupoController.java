package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.EstadoGrupoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EstadoGrupoResponseDto;
import co.edu.unbosque.horaclass.academy.service.EstadoGrupoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estado-grupo")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstadoGrupoController {

    private final EstadoGrupoService service;

    public EstadoGrupoController(EstadoGrupoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EstadoGrupoResponseDto> crear(@RequestBody EstadoGrupoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoGrupoResponseDto> actualizar(@PathVariable Integer id,
                                                             @RequestBody EstadoGrupoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<EstadoGrupoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoGrupoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}