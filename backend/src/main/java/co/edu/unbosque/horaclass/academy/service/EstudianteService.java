package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface EstudianteService {
    EstudianteResponseDto crear(EstudianteRequestDto request);
    EstudianteResponseDto actualizar(int id, EstudianteRequestDto request);
    List<EstudianteResponseDto> listarTodos();
    EstudianteResponseDto obtenerPorId(int id);
    void eliminar(int id);
}