package co.edu.unbosque.horaclass.academy.group.dto;

import co.edu.unbosque.horaclass.academy.course.dto.CourseRespondeDto;
import co.edu.unbosque.horaclass.academy.teacher.dto.TeacherResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponseDto {
    private String idGrupo;

    private CourseRespondeDto curso;

    private TeacherResponseDto profesor;

    private Integer cupoMaximo;

    private Integer cupoMinimo;

    private GroupStateResponseDto estadoGrupo;
}
