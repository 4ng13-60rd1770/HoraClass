package co.edu.unbosque.horaclass.academy.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDto {
    private Long idEstudiante;
    private String primerNombre;
    private String primerApellido;
    private String username;
    private String carrera;
    private Integer semestre;
    private Integer creditos;
}
