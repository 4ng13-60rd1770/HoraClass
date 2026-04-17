package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectivoRequestDto {
    private int idEmpleado;
    private int idCargo;
    private java.time.LocalDate fecha_inicio;
    private java.time.LocalDate fecha_fin;
}
