package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.TipoAulaRequestDto;
import co.edu.unbosque.horaclass.academy.dto.TipoAulaResponseDto;
import co.edu.unbosque.horaclass.academy.service.TipoAulaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-aula")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TipoAulaController {

    private final TipoAulaService service;

    public TipoAulaController(TipoAulaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TipoAulaResponseDto> crear(@RequestBody TipoAulaRequestDto request) {
        TipoAulaResponseDto response = service.crear(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoAulaResponseDto> actualizar(@PathVariable Integer id,
            @RequestBody TipoAulaRequestDto request) {
        TipoAulaResponseDto response = service.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TipoAulaResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoAulaResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
