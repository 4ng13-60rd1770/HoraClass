package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CURSO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @Column(name = "IdCurso")
    private int idCurso;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "Semestre_plan", nullable = false)
    private int semestre_plan;

    @Column(name = "Creditos", nullable = false)
    private int creditos;

    @Column(name = "IdTipoCurso", nullable = false)
    private int idTipoCurso;

    @Column(name = "IdModalidadCurso", nullable = false)
    private int idModalidadCurso;

    @Column(name = "IdPregrado", nullable = false)
    private int idPregrado;

    @Column(name = "IdEstadoCurso", nullable = false)
    private int idEstadoCurso;
}