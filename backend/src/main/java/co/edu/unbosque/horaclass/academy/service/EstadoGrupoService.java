package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface EstadoGrupoService {
    EstadoGrupoResponseDto crear(EstadoGrupoRequestDto request);
    EstadoGrupoResponseDto actualizar(int id, EstadoGrupoRequestDto request);
    List<EstadoGrupoResponseDto> listarTodos();
    EstadoGrupoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}