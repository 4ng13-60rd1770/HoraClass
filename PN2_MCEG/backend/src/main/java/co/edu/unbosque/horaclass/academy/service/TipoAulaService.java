package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface TipoAulaService {
    TipoAulaResponseDto crear(TipoAulaRequestDto request);
    TipoAulaResponseDto actualizar(int id, TipoAulaRequestDto request);
    List<TipoAulaResponseDto> listarTodos();
    TipoAulaResponseDto obtenerPorId(int id);
    void eliminar(int id);
}