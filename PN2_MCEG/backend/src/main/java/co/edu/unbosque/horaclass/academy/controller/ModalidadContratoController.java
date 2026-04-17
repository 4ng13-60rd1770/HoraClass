package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.ModalidadContratoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.ModalidadContratoResponseDto;
import co.edu.unbosque.horaclass.academy.service.ModalidadContratoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modalidad-contrato")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModalidadContratoController {

    private final ModalidadContratoService service;

    public ModalidadContratoController(ModalidadContratoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ModalidadContratoResponseDto> crear(@RequestBody ModalidadContratoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModalidadContratoResponseDto> actualizar(@PathVariable Integer id,
                                                                   @RequestBody ModalidadContratoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<ModalidadContratoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModalidadContratoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}