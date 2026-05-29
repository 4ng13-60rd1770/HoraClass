package co.edu.unbosque.horaclass.academy.teacher.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TeacherResponseDto {

    private Long idProfesor;

    private String nombre;

    private String username;

    private String departamento;

    private String especialidad;

    private Integer cargaHoras;

    private String escalafon;

    private String tipoVinculacion;

    private String restriccionHorario;

    private List<Integer> cursosHabilitados;
}
