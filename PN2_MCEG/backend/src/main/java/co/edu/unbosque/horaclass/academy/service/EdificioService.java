package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface EdificioService {
    EdificioResponseDto crear(EdificioRequestDto request);
    EdificioResponseDto actualizar(int id, EdificioRequestDto request);
    List<EdificioResponseDto> listarTodos();
    EdificioResponseDto obtenerPorId(int id);
    void eliminar(int id);
}