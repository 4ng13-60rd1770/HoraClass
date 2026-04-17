package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.TipoSillasRequestDto;
import co.edu.unbosque.horaclass.academy.dto.TipoSillasResponseDto;
import co.edu.unbosque.horaclass.academy.service.TipoSillasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-sillas")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TipoSillasController {

    private final TipoSillasService service;

    public TipoSillasController(TipoSillasService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TipoSillasResponseDto> crear(@RequestBody TipoSillasRequestDto request) {
        TipoSillasResponseDto response = service.crear(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoSillasResponseDto> actualizar(@PathVariable Integer id,
            @RequestBody TipoSillasRequestDto request) {
        TipoSillasResponseDto response = service.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TipoSillasResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoSillasResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
