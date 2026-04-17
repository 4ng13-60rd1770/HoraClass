package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface EscalafonService {
    EscalafonResponseDto crear(EscalafonRequestDto request);
    EscalafonResponseDto actualizar(int id, EscalafonRequestDto request);
    List<EscalafonResponseDto> listarTodos();
    EscalafonResponseDto obtenerPorId(int id);
    void eliminar(int id);
}