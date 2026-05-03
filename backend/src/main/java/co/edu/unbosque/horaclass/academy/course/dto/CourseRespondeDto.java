package co.edu.unbosque.horaclass.academy.course.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourseRespondeDto {
    private int idCurso;

    private String nombre;

    private int semestre;

    private int creditos;

    private String modalidad;  // Nombre de la modalidad, no ID

    private String tipoCurso;  // Nombre del tipo, no ID
}
