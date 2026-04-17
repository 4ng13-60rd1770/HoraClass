package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfesorResponseDto {
    private int idEmpleado;
    private int idModalidad;
    private int idEscalafon;
    private int carga_horas;
}