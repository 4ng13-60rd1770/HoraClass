package co.edu.unbosque.horaclass.academy.student.service;

import co.edu.unbosque.horaclass.academy.student.dto.StudentRequestDto;
import co.edu.unbosque.horaclass.academy.student.dto.StudentResponseDto;

import java.util.List;

public interface StudentService {
    StudentResponseDto crearEstudiante(StudentRequestDto request);
    StudentResponseDto actualizarEstudiante(Long id, StudentRequestDto request);
    List<StudentResponseDto> listarTodosLosEstudiantes();
    StudentResponseDto obtenerEstudiantePorId(Long id);
    void eliminarEstudiante(Long id);
}
