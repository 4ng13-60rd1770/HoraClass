package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoInscripcionRequestDto {
    private int idEstadoInscripcion;
    private String nombre;
}
