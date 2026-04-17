package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RESTRICCION_PROFESOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestriccionProfesor {

    @Id
    @Column(name = "IdRestriccion")
    private int idRestriccion;

    @Column(name = "IdEmpleado", nullable = false)
    private int idEmpleado;

    @Column(name = "IdDia")
    private Integer idDia;

    @Column(name = "Hora_inicio", nullable = false)
    private java.time.LocalTime hora_inicio;

    @Column(name = "Hora_fin", nullable = false)
    private java.time.LocalTime hora_fin;

    @Column(name = "Descripcion", length = 200)
    private String descripcion;
}