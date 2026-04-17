package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface TipoSillasService {
    TipoSillasResponseDto crear(TipoSillasRequestDto request);
    TipoSillasResponseDto actualizar(int id, TipoSillasRequestDto request);
    List<TipoSillasResponseDto> listarTodos();
    TipoSillasResponseDto obtenerPorId(int id);
    void eliminar(int id);
}