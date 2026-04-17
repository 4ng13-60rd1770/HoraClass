package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface EstudioEmpleadoService {
    EstudioEmpleadoResponseDto crear(EstudioEmpleadoRequestDto request);
    EstudioEmpleadoResponseDto actualizar(int id, EstudioEmpleadoRequestDto request);
    List<EstudioEmpleadoResponseDto> listarTodos();
    EstudioEmpleadoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}