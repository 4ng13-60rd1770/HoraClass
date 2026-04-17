package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface EstadoCursoService {
    EstadoCursoResponseDto crear(EstadoCursoRequestDto request);
    EstadoCursoResponseDto actualizar(int id, EstadoCursoRequestDto request);
    List<EstadoCursoResponseDto> listarTodos();
    EstadoCursoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}