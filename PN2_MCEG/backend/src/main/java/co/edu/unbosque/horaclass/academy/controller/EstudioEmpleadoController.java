package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.EstudioEmpleadoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EstudioEmpleadoResponseDto;
import co.edu.unbosque.horaclass.academy.service.EstudioEmpleadoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudio-empleado")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstudioEmpleadoController {

    private final EstudioEmpleadoService service;

    public EstudioEmpleadoController(EstudioEmpleadoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EstudioEmpleadoResponseDto> crear(@RequestBody EstudioEmpleadoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudioEmpleadoResponseDto> actualizar(@PathVariable Integer id,
                                                                 @RequestBody EstudioEmpleadoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<EstudioEmpleadoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudioEmpleadoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}