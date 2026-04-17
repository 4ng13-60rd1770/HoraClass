package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GRUPO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grupo {

    @Id
    @Column(name = "IdGrupo")
    private int idGrupo;

    @Column(name = "IdCurso", nullable = false)
    private int idCurso;

    @Column(name = "IdPeriodo", nullable = false)
    private int idPeriodo;

    @Column(name = "IdEmpleado", nullable = false)
    private int idEmpleado;

    @Column(name = "Cupo_maximo", nullable = false)
    private int cupo_maximo;

    @Column(name = "Cupo_minimo", nullable = false)
    private int cupo_minimo;

    @Column(name = "IdEstadoGrupo", nullable = false)
    private int idEstadoGrupo;
}