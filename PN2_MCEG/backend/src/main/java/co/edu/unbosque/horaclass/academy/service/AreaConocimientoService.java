package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface AreaConocimientoService {
    AreaConocimientoResponseDto crear(AreaConocimientoRequestDto request);
    AreaConocimientoResponseDto actualizar(int id, AreaConocimientoRequestDto request);
    List<AreaConocimientoResponseDto> listarTodos();
    AreaConocimientoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}