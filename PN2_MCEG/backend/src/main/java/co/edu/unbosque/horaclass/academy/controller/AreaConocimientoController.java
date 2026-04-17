package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.AreaConocimientoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.AreaConocimientoResponseDto;
import co.edu.unbosque.horaclass.academy.service.AreaConocimientoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/area-conocimiento")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AreaConocimientoController {

    private final AreaConocimientoService service;

    public AreaConocimientoController(AreaConocimientoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AreaConocimientoResponseDto> crear(@RequestBody AreaConocimientoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaConocimientoResponseDto> actualizar(@PathVariable Integer id,
                                                                  @RequestBody AreaConocimientoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<AreaConocimientoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaConocimientoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}