package co.edu.unbosque.horaclass.academy.materia.controller;

import co.edu.unbosque.horaclass.academy.materia.dto.MateriaResponseDto;
import co.edu.unbosque.horaclass.academy.materia.service.MateriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/** Endpoints de lectura y auto-asignación para docentes y estudiantes */
@RestController
@RequestMapping("/api/materias")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MateriaPublicController {

    private final MateriaService materiaService;

    public MateriaPublicController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    /** Todas las materias — cualquier usuario autenticado */
    @GetMapping
    public ResponseEntity<List<MateriaResponseDto>> listar() {
        return ResponseEntity.ok(materiaService.listarTodas());
    }

    /** Materias por carrera — estudiante filtra las suyas */
    @GetMapping("/carrera/{carrera}")
    public ResponseEntity<List<MateriaResponseDto>> listarPorCarrera(@PathVariable String carrera) {
        return ResponseEntity.ok(materiaService.listarPorCarrera(carrera));
    }

    /** Materias del docente autenticado */
    @GetMapping("/mis-materias")
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<List<MateriaResponseDto>> misMaterias(Principal principal) {
        return ResponseEntity.ok(materiaService.listarPorDocente(principal.getName()));
    }

    /** Docente se inscribe a una materia */
    @PatchMapping("/{id}/inscribir-docente")
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<MateriaResponseDto> inscribirDocente(
            @PathVariable Long id,
            Principal principal) {
        return ResponseEntity.ok(materiaService.asignarDocente(id, principal.getName()));
    }

    /** Docente se desinscribe de una materia */
    @PatchMapping("/{id}/desinscribir-docente")
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<MateriaResponseDto> desinscribirDocente(
            @PathVariable Long id,
            Principal principal) {
        // Solo puede desinscribirse si es el docente asignado
        MateriaResponseDto materia = materiaService.obtenerPorId(id);
        if (!principal.getName().equals(materia.getDocente())) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(materiaService.liberarDocente(id));
    }
}
