package co.edu.unbosque.horaclass.academy.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String escalafon;
    private String tipoVinculacion;
    private String restriccionHorario;
    /** IDs de cursos que el docente está habilitado a dictar (tabla PRO_CUR). */
    private List<Integer> cursosHabilitados;
}
