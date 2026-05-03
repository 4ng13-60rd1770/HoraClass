package co.edu.unbosque.horaclass.academy.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class TeacherResponseDto {

    private Long idProfesor;

    private String nombre;

    private String username;

    private String departamento;

    private String especialidad;

    private Integer cargaHoras;

    private String escalafon;
}
