package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PERIODO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Periodo {

    @Id
    @Column(name = "IdPeriodo")
    private int idPeriodo;

    @Column(name = "Anio", nullable = false)
    private int anio;

    @Column(name = "Semestre", nullable = false)
    private int semestre;

    @Column(name = "Fecha_inicio", nullable = false)
    private java.time.LocalDate fecha_inicio;

    @Column(name = "Fecha_fin", nullable = false)
    private java.time.LocalDate fecha_fin;

    @Column(name = "Activo", nullable = false)
    private boolean activo;
}