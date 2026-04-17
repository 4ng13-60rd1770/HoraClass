package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface NivelAcademicoService {
    NivelAcademicoResponseDto crear(NivelAcademicoRequestDto request);
    NivelAcademicoResponseDto actualizar(int id, NivelAcademicoRequestDto request);
    List<NivelAcademicoResponseDto> listarTodos();
    NivelAcademicoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}