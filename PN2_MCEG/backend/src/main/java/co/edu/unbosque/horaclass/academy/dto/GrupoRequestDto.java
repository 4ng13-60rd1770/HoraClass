package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoRequestDto {
    private Integer idGrupo;
    private Integer idCurso;
    private Integer idPeriodo;
    private Integer idEmpleado;
    private Integer cupoMaximo;
    private Integer cupoMinimo;
    private Integer idEstadoGrupo;
}
