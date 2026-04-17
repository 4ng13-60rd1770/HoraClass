package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface ModalidadContratoService {
    ModalidadContratoResponseDto crear(ModalidadContratoRequestDto request);
    ModalidadContratoResponseDto actualizar(int id, ModalidadContratoRequestDto request);
    List<ModalidadContratoResponseDto> listarTodos();
    ModalidadContratoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}