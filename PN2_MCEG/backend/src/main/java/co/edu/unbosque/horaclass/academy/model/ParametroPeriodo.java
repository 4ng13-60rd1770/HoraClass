package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PARAMETRO_PERIODO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametroPeriodo {

    @Id
    @Column(name = "IdPeriodo")
    private int idPeriodo;

    @Column(name = "Cupo_maximo", nullable = false)
    private int cupo_maximo;

    @Column(name = "Tolerancia_pct", nullable = false)
    private int tolerancia_pct;

    @Column(name = "Cupo_minimo_cierre", nullable = false)
    private int cupo_minimo_cierre;

    @Column(name = "Max_sesiones_semana", nullable = false)
    private int max_sesiones_semana;

    @Column(name = "Hora_inicio_franja", nullable = false)
    private java.time.LocalTime hora_inicio_franja;

    @Column(name = "Hora_fin_franja_lv", nullable = false)
    private java.time.LocalTime hora_fin_franja_lv;

    @Column(name = "Hora_fin_franja_sa", nullable = false)
    private java.time.LocalTime hora_fin_franja_sa;

    @Column(name = "Hora_inicio_almuerzo", nullable = false)
    private java.time.LocalTime hora_inicio_almuerzo;

    @Column(name = "Hora_fin_almuerzo", nullable = false)
    private java.time.LocalTime hora_fin_almuerzo;

    @Column(name = "Duracion_sesion_horas", nullable = false)
    private int duracion_sesion_horas;
}