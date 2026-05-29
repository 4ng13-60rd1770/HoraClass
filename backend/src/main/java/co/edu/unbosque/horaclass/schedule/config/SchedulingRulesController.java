package co.edu.unbosque.horaclass.schedule.config;

import co.edu.unbosque.horaclass.schedule.config.dto.SchedulingRulesDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/scheduling")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SchedulingRulesController {

    private final SchedulingRulesService schedulingRulesService;

    public SchedulingRulesController(SchedulingRulesService schedulingRulesService) {
        this.schedulingRulesService = schedulingRulesService;
    }

    @GetMapping("/reglas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SchedulingRulesDto> obtenerReglas() {
        return ResponseEntity.ok(schedulingRulesService.obtenerReglas());
    }

    @PutMapping("/reglas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> actualizarReglas(@RequestBody SchedulingRulesDto dto) {
        try {
            return ResponseEntity.ok(schedulingRulesService.actualizarReglas(dto));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

    @PostMapping("/reglas/restablecer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SchedulingRulesDto> restablecerDefecto() {
        return ResponseEntity.ok(schedulingRulesService.restablecerDefecto());
    }
}
