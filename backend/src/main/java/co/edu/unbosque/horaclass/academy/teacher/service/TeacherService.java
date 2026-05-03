package co.edu.unbosque.horaclass.academy.teacher.service;

import co.edu.unbosque.horaclass.academy.teacher.dto.TeacherRequestDto;
import co.edu.unbosque.horaclass.academy.teacher.dto.TeacherResponseDto;

import java.util.List;

public interface TeacherService {
    TeacherResponseDto crearProfesor(TeacherRequestDto request);
    List<TeacherResponseDto> listarTodosLosProfesores();
    TeacherResponseDto obtenerProfesorPorId(Long id);
    TeacherResponseDto actualizarProfesor(Long id, TeacherRequestDto request);
    void eliminarProfesor(Long id);
}
