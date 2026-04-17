package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.*;
import java.util.List;

public interface CargoService {
    CargoResponseDto crear(CargoRequestDto request);
    CargoResponseDto actualizar(int id, CargoRequestDto request);
    List<CargoResponseDto> listarTodos();
    CargoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}