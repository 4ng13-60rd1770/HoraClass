package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EdificioResponseDto {
    private int idEdificio;
    private String nombre;
}