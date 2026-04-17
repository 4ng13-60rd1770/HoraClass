package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface ProfesorService {
    ProfesorResponseDto crear(ProfesorRequestDto request);
    ProfesorResponseDto actualizar(int id, ProfesorRequestDto request);
    List<ProfesorResponseDto> listarTodos();
    ProfesorResponseDto obtenerPorId(int id);
    void eliminar(int id);
}