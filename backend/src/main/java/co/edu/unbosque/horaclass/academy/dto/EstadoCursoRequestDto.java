package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCursoRequestDto {
    private int idEstadoCurso;
    private String nombre;
}