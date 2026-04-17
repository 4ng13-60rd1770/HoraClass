package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface EmpleadoService {
    EmpleadoResponseDto crear(EmpleadoRequestDto request);
    EmpleadoResponseDto actualizar(int id, EmpleadoRequestDto request);
    List<EmpleadoResponseDto> listarTodos();
    EmpleadoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}