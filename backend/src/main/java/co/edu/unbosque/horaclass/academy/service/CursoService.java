package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface CursoService {
    CursoResponseDto crear(CursoRequestDto request);
    CursoResponseDto actualizar(int id, CursoRequestDto request);
    List<CursoResponseDto> listarTodos();
    CursoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}