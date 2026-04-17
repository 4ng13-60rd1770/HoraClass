package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface EstadoInscripcionService {
    EstadoInscripcionResponseDto crear(EstadoInscripcionRequestDto request);
    EstadoInscripcionResponseDto actualizar(int id, EstadoInscripcionRequestDto request);
    List<EstadoInscripcionResponseDto> listarTodos();
    EstadoInscripcionResponseDto obtenerPorId(int id);
    void eliminar(int id);
}