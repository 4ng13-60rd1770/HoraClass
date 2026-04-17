package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodoRequestDto {
    private int idPeriodo;
    private int anio;
    private int semestre;
    private java.time.LocalDate fecha_inicio;
    private java.time.LocalDate fecha_fin;
    private boolean activo;
}