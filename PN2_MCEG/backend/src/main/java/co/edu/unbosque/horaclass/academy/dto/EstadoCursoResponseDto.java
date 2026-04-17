package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoCursoResponseDto {
    private int idEstadoCurso;
    private String nombre;
}
