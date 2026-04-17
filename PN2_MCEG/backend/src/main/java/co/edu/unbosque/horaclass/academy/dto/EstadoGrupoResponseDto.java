package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoGrupoResponseDto {
    private int idEstadoGrupo;
    private String nombre;
}
