package co.edu.unbosque.horaclass.academy.course.service;

import co.edu.unbosque.horaclass.academy.course.dto.CourseRequestDto;
import co.edu.unbosque.horaclass.academy.course.dto.CourseRespondeDto;

import java.util.List;

public interface CourseService {
    CourseRespondeDto crearCurso(CourseRequestDto request);

    CourseRespondeDto actualizarCurso(int idCurso, CourseRequestDto reques);

    List<CourseRespondeDto> listarTodosLosCursos();

    CourseRespondeDto obtenerCursoPorId(int idCurso);

    void eliminarCurso (int idCurso);

}
