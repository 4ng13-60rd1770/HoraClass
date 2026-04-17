package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface SesionService {
    SesionResponseDto crear(SesionRequestDto request);
    SesionResponseDto actualizar(int id, SesionRequestDto request);
    List<SesionResponseDto> listarTodos();
    SesionResponseDto obtenerPorId(int id);
    void eliminar(int id);
}