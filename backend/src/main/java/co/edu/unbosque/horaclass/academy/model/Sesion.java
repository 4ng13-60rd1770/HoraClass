package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SESION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sesion {

    @Id
    @Column(name = "IdSesion")
    private int idSesion;

    @Column(name = "IdGrupo", nullable = false)
    private int idGrupo;

    @Column(name = "IdAula", nullable = false)
    private int idAula;

    @Column(name = "IdDia", nullable = false)
    private int idDia;

    @Column(name = "Hora_inicio", nullable = false)
    private java.time.LocalTime hora_inicio;

    @Column(name = "Hora_fin", nullable = false)
    private java.time.LocalTime hora_fin;
}