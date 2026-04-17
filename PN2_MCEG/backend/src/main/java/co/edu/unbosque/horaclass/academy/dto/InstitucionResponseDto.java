package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstitucionResponseDto {
    private int idInstitucion;
    private String nombre;
}