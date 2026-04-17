package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParametroPeriodoResponseDto {
    private int idPeriodo;
    private int cupo_maximo;
    private int tolerancia_pct;
    private int cupo_minimo_cierre;
    private int max_sesiones_semana;
    private java.time.LocalTime hora_inicio_franja;
    private java.time.LocalTime hora_fin_franja_lv;
    private java.time.LocalTime hora_fin_franja_sa;
    private java.time.LocalTime hora_inicio_almuerzo;
    private java.time.LocalTime hora_fin_almuerzo;
    private int duracion_sesion_horas;
}