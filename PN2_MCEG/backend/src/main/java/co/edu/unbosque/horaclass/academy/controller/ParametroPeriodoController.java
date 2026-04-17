package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.ParametroPeriodoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.ParametroPeriodoResponseDto;
import co.edu.unbosque.horaclass.academy.service.ParametroPeriodoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parametro-periodo")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ParametroPeriodoController {

    private final ParametroPeriodoService service;

    public ParametroPeriodoController(ParametroPeriodoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ParametroPeriodoResponseDto> crear(@RequestBody ParametroPeriodoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParametroPeriodoResponseDto> actualizar(@PathVariable int id,
            @RequestBody ParametroPeriodoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<ParametroPeriodoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParametroPeriodoResponseDto> obtenerPorId(@PathVariable int id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}