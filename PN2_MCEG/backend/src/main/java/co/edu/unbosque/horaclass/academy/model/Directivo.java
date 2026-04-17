package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "DIRECTIVO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Directivo {

    @Id
    @Column(name = "IdEmpleado")
    private int idEmpleado;

    @Column(name = "IdCargo", nullable = false)
    private int idCargo;

    @Column(name = "Fecha_inicio", nullable = false)
    private java.time.LocalDate fecha_inicio;

    @Column(name = "Fecha_fin")
    private java.time.LocalDate fecha_fin;
}