package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.NivelAcademicoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.NivelAcademicoResponseDto;
import co.edu.unbosque.horaclass.academy.service.NivelAcademicoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nivel-academico")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NivelAcademicoController {

    private final NivelAcademicoService service;

    public NivelAcademicoController(NivelAcademicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<NivelAcademicoResponseDto> crear(@RequestBody NivelAcademicoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NivelAcademicoResponseDto> actualizar(@PathVariable int id,
            @RequestBody NivelAcademicoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<NivelAcademicoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NivelAcademicoResponseDto> obtenerPorId(@PathVariable int id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}