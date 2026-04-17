package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface ModalidadCursoService {
    ModalidadCursoResponseDto crear(ModalidadCursoRequestDto request);
    ModalidadCursoResponseDto actualizar(int id, ModalidadCursoRequestDto request);
    List<ModalidadCursoResponseDto> listarTodos();
    ModalidadCursoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}