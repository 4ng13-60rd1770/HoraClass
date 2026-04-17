package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfesorRequestDto {
    private int idEmpleado;
    private int idModalidad;
    private int idEscalafon;
    private int carga_horas;
}