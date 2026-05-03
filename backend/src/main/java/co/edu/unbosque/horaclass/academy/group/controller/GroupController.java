package co.edu.unbosque.horaclass.academy.group.controller;

import co.edu.unbosque.horaclass.academy.group.dto.GroupRequestDto;
import co.edu.unbosque.horaclass.academy.group.dto.GroupResponseDto;
import co.edu.unbosque.horaclass.academy.group.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/grupos/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupResponseDto> crearGrupo(@RequestBody GroupRequestDto request) {
        System.out.println("LLEGÓ EL REQUEST");
        GroupResponseDto grupo = groupService.crearGrupo(request);
        return new ResponseEntity<>(grupo, HttpStatus.CREATED);
    }

    @GetMapping("/grupos/{idGrupo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupResponseDto> obtenerGrupoPorId(
            @PathVariable String idGrupo) {
        GroupResponseDto grupo = groupService.obtenerGrupoPorId(idGrupo);
        return ResponseEntity.ok(grupo);
    }

    @GetMapping("/grupos/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GroupResponseDto>> listarTodosLosGrupos() {
        List<GroupResponseDto> grupos = groupService.listarTodosLosGrupos();
        return ResponseEntity.ok(grupos);
    }
    @PutMapping("/grupos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupResponseDto> actualizarGrupo(
            @RequestBody GroupRequestDto request) {
        GroupResponseDto grupo = groupService.actualizarGrupo(request);
        return ResponseEntity.ok(grupo);
    }
    @DeleteMapping("/grupos/{idGrupo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarGrupo(@PathVariable String idGrupo) {
        groupService.eliminarGrupo(idGrupo);
        return ResponseEntity.noContent().build();
    }
}
