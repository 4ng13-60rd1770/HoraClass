package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.EscalafonRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EscalafonResponseDto;
import co.edu.unbosque.horaclass.academy.service.EscalafonService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/escalafon")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EscalafonController {

    private final EscalafonService service;

    public EscalafonController(EscalafonService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EscalafonResponseDto> crear(@RequestBody EscalafonRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EscalafonResponseDto> actualizar(@PathVariable Integer id,
                                                           @RequestBody EscalafonRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<EscalafonResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscalafonResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}