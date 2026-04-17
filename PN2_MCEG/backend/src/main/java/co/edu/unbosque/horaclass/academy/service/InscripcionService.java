package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface InscripcionService {
    InscripcionResponseDto crear(InscripcionRequestDto request);
    InscripcionResponseDto actualizar(InscripcionRequestDto request);
    List<InscripcionResponseDto> listarTodos();
    InscripcionResponseDto obtenerPorId(int idEstudiante, int idGrupo);
    void eliminar(int idEstudiante, int idGrupo);
}