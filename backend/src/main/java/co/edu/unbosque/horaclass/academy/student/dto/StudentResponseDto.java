package co.edu.unbosque.horaclass.academy.student.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentResponseDto {
    private Long idEstudiante;
    private String nombre;
    private String username;
    private String carrera;
    private Integer semestre;
    private Integer creditos;
    private String estado;
}
