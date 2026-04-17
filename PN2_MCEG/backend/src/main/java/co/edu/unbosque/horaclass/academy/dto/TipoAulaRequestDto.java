package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoAulaRequestDto {
    private int idTipoAula;
    private String nombre;
}