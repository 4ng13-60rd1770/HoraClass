package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoRequestDto {
    private int idCurso;
    private String nombre;
    private int semestre_plan;
    private int creditos;
    private int idTipoCurso;
    private int idModalidadCurso;
    private int idPregrado;
    private int idEstadoCurso;
}


