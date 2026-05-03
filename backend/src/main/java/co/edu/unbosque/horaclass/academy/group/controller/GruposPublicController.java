package co.edu.unbosque.horaclass.academy.group.controller;

import co.edu.unbosque.horaclass.academy.group.dto.GroupResponseDto;
import co.edu.unbosque.horaclass.academy.group.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GruposPublicController {

    private final GroupService groupService;

    public GruposPublicController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupResponseDto>> listarGrupos() {
        return ResponseEntity.ok(groupService.listarTodosLosGrupos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDto> obtenerGrupo(@PathVariable String id) {
        return ResponseEntity.ok(groupService.obtenerGrupoPorId(id));
    }
}
