package co.edu.unbosque.horaclass.schedule.generation.controller;

import co.edu.unbosque.horaclass.schedule.generation.dto.ScheduleResponseDto;
import co.edu.unbosque.horaclass.schedule.generation.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * Genera el horario académico para un semestre.
     * POST /api/horarios/generar/{semestre}
     */
    @PostMapping("/generar/{semestre}")
    public ResponseEntity<ScheduleResponseDto> generar(@PathVariable String semestre) {
        try {
            ScheduleResponseDto resultado = scheduleService.generarHorario(semestre);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Obtiene el horario de un semestre con todas sus entries y conflictos.
     * GET /api/horarios/{semestre}
     */
    @GetMapping("/{semestre}")
    public ResponseEntity<ScheduleResponseDto> obtener(@PathVariable String semestre) {
        try {
            return ResponseEntity.ok(scheduleService.obtenerPorSemestre(semestre));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lista todos los horarios generados.
     * GET /api/horarios
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> listarTodos() {
        return ResponseEntity.ok(scheduleService.listarTodos());
    }

    /**
     * Publica un horario (solo si no hay conflictos pendientes).
     * PATCH /api/horarios/{semestre}/publicar
     */
    @PatchMapping("/{semestre}/publicar")
    public ResponseEntity<?> publicar(@PathVariable String semestre) {
        try {
            return ResponseEntity.ok(scheduleService.publicar(semestre));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Elimina un horario para permitir regeneración.
     * DELETE /api/horarios/{semestre}
     */
    @DeleteMapping("/{semestre}")
    public ResponseEntity<Void> eliminar(@PathVariable String semestre) {
        try {
            scheduleService.eliminar(semestre);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
