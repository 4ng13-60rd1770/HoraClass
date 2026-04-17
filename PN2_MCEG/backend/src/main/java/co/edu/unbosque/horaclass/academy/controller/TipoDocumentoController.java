package co.edu.unbosque.horaclass.academy.controller;

import co.edu.unbosque.horaclass.academy.dto.TipoDocumentoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.TipoDocumentoResponseDto;
import co.edu.unbosque.horaclass.academy.service.TipoDocumentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-documento")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TipoDocumentoController {

    private final TipoDocumentoService service;

    public TipoDocumentoController(TipoDocumentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TipoDocumentoResponseDto> crear(@RequestBody TipoDocumentoRequestDto request) {
        TipoDocumentoResponseDto response = service.crear(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDocumentoResponseDto> actualizar(@PathVariable Integer id,
            @RequestBody TipoDocumentoRequestDto request) {
        TipoDocumentoResponseDto response = service.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TipoDocumentoResponseDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumentoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
