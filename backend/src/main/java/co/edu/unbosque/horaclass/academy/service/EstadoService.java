package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface EstadoService {
    EstadoResponseDto crear(EstadoRequestDto request);
    EstadoResponseDto actualizar(int id, EstadoRequestDto request);
    List<EstadoResponseDto> listarTodos();
    EstadoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}