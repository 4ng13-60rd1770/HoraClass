package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoResponseDto {
    private int idCurso;
    private String nombre;
    private int semestre_plan;
    private int creditos;
    private int idTipoCurso;
    private int idModalidadCurso;
    private int idPregrado;
    private int idEstadoCurso;
}


