package co.edu.unbosque.horaclass.academy.group.service;

import co.edu.unbosque.horaclass.academy.group.dto.GroupRequestDto;
import co.edu.unbosque.horaclass.academy.group.dto.GroupResponseDto;

import java.util.List;

public interface GroupService {
    GroupResponseDto crearGrupo(GroupRequestDto request);

    GroupResponseDto actualizarGrupo(GroupRequestDto request);

    List<GroupResponseDto> listarTodosLosGrupos();

    List<GroupResponseDto> listarGruposPorCurso(Integer idCurso);

    List<GroupResponseDto> listarGruposPorProfesor(Long idProfesor);

    GroupResponseDto obtenerGrupoPorId(String idGrupo);

    void eliminarGrupo(String idGrupo);
}
