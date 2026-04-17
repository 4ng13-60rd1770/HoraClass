package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface ProfesorCursoService {

    ProfesorCursoResponseDto crear(ProfesorCursoRequestDto request);

    ProfesorCursoResponseDto actualizar(int idEmpleado, int idCurso, ProfesorCursoRequestDto request);

    List<ProfesorCursoResponseDto> listarTodos();

    ProfesorCursoResponseDto obtenerPorId(int idEmpleado, int idCurso);

    void eliminar(int idEmpleado, int idCurso);
}