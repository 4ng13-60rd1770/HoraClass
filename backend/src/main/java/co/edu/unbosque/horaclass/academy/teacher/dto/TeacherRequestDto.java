package co.edu.unbosque.horaclass.academy.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRequestDto {
    private Long idProfesor;
    private String primerNombre;
    private String primerApellido;
    private String username;
    private String carrera;
    private Integer cargaHoras;
}
