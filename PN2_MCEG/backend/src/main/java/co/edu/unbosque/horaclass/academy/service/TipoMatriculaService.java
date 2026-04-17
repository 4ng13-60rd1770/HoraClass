package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface TipoMatriculaService {
    TipoMatriculaResponseDto crear(TipoMatriculaRequestDto request);
    TipoMatriculaResponseDto actualizar(int id, TipoMatriculaRequestDto request);
    List<TipoMatriculaResponseDto> listarTodos();
    TipoMatriculaResponseDto obtenerPorId(int id);
    void eliminar(int id);
}