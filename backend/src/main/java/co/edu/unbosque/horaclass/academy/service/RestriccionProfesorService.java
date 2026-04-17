package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.RestriccionProfesorRequestDto;
import co.edu.unbosque.horaclass.academy.dto.RestriccionProfesorResponseDto;

import java.util.List;

public interface RestriccionProfesorService {

    RestriccionProfesorResponseDto crear(RestriccionProfesorRequestDto request);

    RestriccionProfesorResponseDto actualizar(int id, RestriccionProfesorRequestDto request);

    List<RestriccionProfesorResponseDto> listarTodos();

    RestriccionProfesorResponseDto obtenerPorId(int id);

    void eliminar(int id);
}