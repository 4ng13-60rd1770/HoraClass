package co.edu.unbosque.horaclass.academy.classroom.service;

import co.edu.unbosque.horaclass.academy.classroom.dto.ClassroomRequestDto;
import co.edu.unbosque.horaclass.academy.classroom.dto.ClassroomResponseDto;

import java.util.List;

public interface ClassroomService {
    ClassroomResponseDto crearSalon(ClassroomRequestDto request);
    ClassroomResponseDto actualizarSalon(Integer id, ClassroomRequestDto request);
    List<ClassroomResponseDto> listarTodosLosSalones();
    ClassroomResponseDto obtenerSalonPorId(Integer id);
    void eliminarSalon(Integer id);
}
