package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoMatriculaResponseDto {
    private int idTipoMatricula;
    private String nombre;
}