package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PregradoResponseDto {
    private int idPregrado;
    private String nombre;
    private int idDepartamento;
}