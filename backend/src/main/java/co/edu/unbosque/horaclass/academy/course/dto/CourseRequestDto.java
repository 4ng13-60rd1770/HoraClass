package co.edu.unbosque.horaclass.academy.course.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseRequestDto {

    private int idCurso;
    private String nombre;
    private int semestre;
    private int creditos;
    private int idModalidad;
    private int idTipoCurso;
}
