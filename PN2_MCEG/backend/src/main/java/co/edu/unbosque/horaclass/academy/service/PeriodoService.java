package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface PeriodoService {
    PeriodoResponseDto crear(PeriodoRequestDto request);
    PeriodoResponseDto actualizar(int id, PeriodoRequestDto request);
    List<PeriodoResponseDto> listarTodos();
    PeriodoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}