package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface PregradoService {
    PregradoResponseDto crear(PregradoRequestDto request);
    PregradoResponseDto actualizar(int id, PregradoRequestDto request);
    List<PregradoResponseDto> listarTodos();
    PregradoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}