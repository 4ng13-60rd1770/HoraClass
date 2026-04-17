package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoCursoResponseDto {
    private int idTipoCurso;
    private String nombre;
}