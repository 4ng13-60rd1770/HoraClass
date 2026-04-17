package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoCursoRequestDto {
    private int idTipoCurso;
    private String nombre;
}