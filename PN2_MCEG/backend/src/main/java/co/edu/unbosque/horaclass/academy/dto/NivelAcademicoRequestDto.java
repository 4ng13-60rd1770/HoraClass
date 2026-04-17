package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NivelAcademicoRequestDto {
    private int idNivel;
    private String nombre;
}