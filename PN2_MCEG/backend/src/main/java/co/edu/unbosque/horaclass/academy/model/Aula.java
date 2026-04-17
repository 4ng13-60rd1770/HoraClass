package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AULA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aula {

    @Id
    @Column(name = "IdAula")
    private int idAula;

    @Column(name = "IdEdificio", nullable = false)
    private int idEdificio;

    @Column(name = "IdTipoAula", nullable = false)
    private int idTipoAula;

    @Column(name = "IdTipoSillas", nullable = false)
    private int idTipoSillas;

    @Column(name = "Capacidad", nullable = false)
    private int capacidad;

    @Column(name = "Nombre", length = 50)
    private String nombre;
}