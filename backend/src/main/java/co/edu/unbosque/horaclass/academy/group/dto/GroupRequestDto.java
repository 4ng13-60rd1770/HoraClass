package co.edu.unbosque.horaclass.academy.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequestDto {
    private String idGrupo;

    private int idCurso;

    private Long idProfesor;

    private int cupoMaximo;

    private int cupoMinimo;

    private int idEstadoGrupo;
}
