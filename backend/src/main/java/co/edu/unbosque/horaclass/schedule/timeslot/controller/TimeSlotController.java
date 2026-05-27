package co.edu.unbosque.horaclass.schedule.timeslot.controller;

import co.edu.unbosque.horaclass.schedule.timeslot.dto.TimeSlotRequestDto;
import co.edu.unbosque.horaclass.schedule.timeslot.dto.TimeSlotResponseDto;
import co.edu.unbosque.horaclass.schedule.timeslot.service.TimeSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/franjas")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @PostMapping
    public ResponseEntity<TimeSlotResponseDto> crear(@RequestBody TimeSlotRequestDto request) {
        TimeSlotResponseDto created = timeSlotService.crear(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TimeSlotResponseDto>> listarTodas() {
        return ResponseEntity.ok(timeSlotService.listarTodas());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<TimeSlotResponseDto>> listarDisponibles() {
        return ResponseEntity.ok(timeSlotService.listarDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeSlotResponseDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(timeSlotService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeSlotResponseDto> actualizar(@PathVariable Long id,
                                                          @RequestBody TimeSlotRequestDto request) {
        return ResponseEntity.ok(timeSlotService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        timeSlotService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
