package co.edu.unbosque.horaclass.academy.classroom.controller;

import co.edu.unbosque.horaclass.academy.classroom.dto.ClassroomRequestDto;
import co.edu.unbosque.horaclass.academy.classroom.dto.ClassroomResponseDto;
import co.edu.unbosque.horaclass.academy.classroom.service.ClassroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PostMapping("/salones/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomResponseDto> crearSalon(@RequestBody ClassroomRequestDto request) {
        return new ResponseEntity<>(classroomService.crearSalon(request), HttpStatus.CREATED);
    }

    @GetMapping("/salones")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClassroomResponseDto>> listarSalones() {
        return ResponseEntity.ok(classroomService.listarTodosLosSalones());
    }

    @GetMapping("/salones/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomResponseDto> obtenerSalon(@PathVariable Integer id) {
        return ResponseEntity.ok(classroomService.obtenerSalonPorId(id));
    }

    @PutMapping("/salones/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomResponseDto> actualizarSalon(
            @PathVariable Integer id,
            @RequestBody ClassroomRequestDto request) {
        return ResponseEntity.ok(classroomService.actualizarSalon(id, request));
    }

    @DeleteMapping("/salones/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarSalon(@PathVariable Integer id) {
        classroomService.eliminarSalon(id);
        return ResponseEntity.noContent().build();
    }
}
