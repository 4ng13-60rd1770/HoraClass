package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestriccionProfesorRequestDto {
    private int idRestriccion;
    private int idEmpleado;
    private Integer idDia;
    private java.time.LocalTime hora_inicio;
    private java.time.LocalTime hora_fin;
    private String descripcion;
}