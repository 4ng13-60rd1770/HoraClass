package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourseRespondeDto {
    private Integer idCurso;

    private String nombre;

    private Integer semestre;

    private Integer creditos;

    private String modalidad;  // Nombre de la modalidad, no ID

    private String tipoCurso;  // Nombre del tipo, no ID
}
