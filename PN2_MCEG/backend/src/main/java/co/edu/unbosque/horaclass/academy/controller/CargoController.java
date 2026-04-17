package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.CargoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.CargoResponseDto;
import co.edu.unbosque.horaclass.academy.service.CargoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargo")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CargoController {

    private final CargoService service;

    public CargoController(CargoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CargoResponseDto> crear(@RequestBody CargoRequestDto request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoResponseDto> actualizar(@PathVariable Integer id,
                                                       @RequestBody CargoRequestDto request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<CargoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}