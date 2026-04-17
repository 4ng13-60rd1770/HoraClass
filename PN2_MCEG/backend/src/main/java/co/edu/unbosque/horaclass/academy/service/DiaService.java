package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface DiaService {
    DiaResponseDto crear(DiaRequestDto request);
    DiaResponseDto actualizar(int id, DiaRequestDto request);
    List<DiaResponseDto> listarTodos();
    DiaResponseDto obtenerPorId(int id);
    void eliminar(int id);
}