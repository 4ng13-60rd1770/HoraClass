package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface GrupoService {
    GrupoResponseDto crear(GrupoRequestDto request);
    GrupoResponseDto actualizar(int id, GrupoRequestDto request);
    List<GrupoResponseDto> listarTodos();
    GrupoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}