package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PregradoRequestDto {
    private int idPregrado;
    private String nombre;
    private int idDepartamento;
}