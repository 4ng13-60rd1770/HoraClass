package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaConocimientoResponseDto {
    private int idArea;
    private String nombre;
}