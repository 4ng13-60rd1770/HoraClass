package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoSillasResponseDto {
    private int idTipoSillas;
    private String nombre;
}