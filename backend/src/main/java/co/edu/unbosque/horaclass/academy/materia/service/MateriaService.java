package co.edu.unbosque.horaclass.academy.materia.service;

import co.edu.unbosque.horaclass.academy.materia.dto.MateriaRequestDto;
import co.edu.unbosque.horaclass.academy.materia.dto.MateriaResponseDto;

import java.util.List;

public interface MateriaService {
    MateriaResponseDto crear(MateriaRequestDto request);
    MateriaResponseDto actualizar(Long id, MateriaRequestDto request);
    MateriaResponseDto obtenerPorId(Long id);
    List<MateriaResponseDto> listarTodas();
    List<MateriaResponseDto> listarPorCarrera(String carrera);
    List<MateriaResponseDto> listarPorDocente(String docente);
    void eliminar(Long id);
    MateriaResponseDto asignarDocente(Long id, String usernameDocente);
    MateriaResponseDto liberarDocente(Long id);
}
