package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface DepartamentoService {
    DepartamentoResponseDto crear(DepartamentoRequestDto request);
    DepartamentoResponseDto actualizar(int id, DepartamentoRequestDto request);
    List<DepartamentoResponseDto> listarTodos();
    DepartamentoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}