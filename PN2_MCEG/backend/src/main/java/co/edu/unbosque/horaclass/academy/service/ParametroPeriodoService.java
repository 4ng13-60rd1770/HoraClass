package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface ParametroPeriodoService {
    ParametroPeriodoResponseDto crear(ParametroPeriodoRequestDto request);
    ParametroPeriodoResponseDto actualizar(int id, ParametroPeriodoRequestDto request);
    List<ParametroPeriodoResponseDto> listarTodos();
    ParametroPeriodoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}