package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface InstitucionService {
    InstitucionResponseDto crear(InstitucionRequestDto request);
    InstitucionResponseDto actualizar(int id, InstitucionRequestDto request);
    List<InstitucionResponseDto> listarTodos();
    InstitucionResponseDto obtenerPorId(int id);
    void eliminar(int id);
}