package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface TipoCursoService {
    TipoCursoResponseDto crear(TipoCursoRequestDto request);
    TipoCursoResponseDto actualizar(int id, TipoCursoRequestDto request);
    List<TipoCursoResponseDto> listarTodos();
    TipoCursoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}