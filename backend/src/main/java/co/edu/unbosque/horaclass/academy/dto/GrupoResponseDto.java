package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoResponseDto {
    private int idGrupo;
    private int idCurso;
    private int idPeriodo;
    private int idEmpleado;
    private int cupo_maximo;
    private int cupo_minimo;
    private int idEstadoGrupo;
}