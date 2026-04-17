package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelAcademicoResponseDto {
    private int idNivel;
    private String nombre;
}