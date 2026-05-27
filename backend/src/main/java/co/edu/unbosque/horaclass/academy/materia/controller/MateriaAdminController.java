package co.edu.unbosque.horaclass.academy.materia.controller;

import co.edu.unbosque.horaclass.academy.materia.dto.MateriaRequestDto;
import co.edu.unbosque.horaclass.academy.materia.dto.MateriaResponseDto;
import co.edu.unbosque.horaclass.academy.materia.service.MateriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** CRUD completo de materias — solo ROLE_ADMIN */
@RestController
@RequestMapping("/api/admin/materias")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MateriaAdminController {

    private final MateriaService materiaService;

    public MateriaAdminController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MateriaResponseDto> crear(@RequestBody MateriaRequestDto request) {
        return new ResponseEntity<>(materiaService.crear(request), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MateriaResponseDto>> listar() {
        return ResponseEntity.ok(materiaService.listarTodas());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MateriaResponseDto> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(materiaService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MateriaResponseDto> actualizar(
            @PathVariable Long id,
            @RequestBody MateriaRequestDto request) {
        return ResponseEntity.ok(materiaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        materiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
