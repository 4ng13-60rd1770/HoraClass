package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoAulaResponseDto {
    private int idTipoAula;
    private String nombre;
}