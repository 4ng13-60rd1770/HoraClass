package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectivoResponseDto {
    private int idEmpleado;
    private int idCargo;
    private java.time.LocalDate fecha_inicio;
    private java.time.LocalDate fecha_fin;
}
