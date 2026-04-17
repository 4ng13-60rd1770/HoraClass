package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface AulaService {
    AulaResponseDto crear(AulaRequestDto request);
    AulaResponseDto actualizar(int id, AulaRequestDto request);
    List<AulaResponseDto> listarTodos();
    AulaResponseDto obtenerPorId(int id);
    void eliminar(int id);
}