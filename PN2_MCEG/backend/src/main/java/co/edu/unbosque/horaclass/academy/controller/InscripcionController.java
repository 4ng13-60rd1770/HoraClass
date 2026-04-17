package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.InscripcionRequestDto;
import co.edu.unbosque.horaclass.academy.dto.InscripcionResponseDto;
import co.edu.unbosque.horaclass.academy.service.InscripcionService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripcion")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InscripcionController {

    private final InscripcionService service;

    public InscripcionController(InscripcionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InscripcionResponseDto> crear(@RequestBody InscripcionRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InscripcionResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{idEstudiante}/{idGrupo}")
    public ResponseEntity<InscripcionResponseDto> obtenerPorId(@PathVariable int idEstudiante,
                                                               @PathVariable int idGrupo) {
        return ResponseEntity.ok(service.obtenerPorId(idEstudiante, idGrupo));
    }

    @DeleteMapping("/{idEstudiante}/{idGrupo}")
    public ResponseEntity<Void> eliminar(@PathVariable int idEstudiante,
                                         @PathVariable int idGrupo) {
        service.eliminar(idEstudiante, idGrupo);
        return ResponseEntity.noContent().build();
    }
}