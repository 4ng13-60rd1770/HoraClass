package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface DirectivoService {
    DirectivoResponseDto crear(DirectivoRequestDto request);
    DirectivoResponseDto actualizar(int id, DirectivoRequestDto request);
    List<DirectivoResponseDto> listarTodos();
    DirectivoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}