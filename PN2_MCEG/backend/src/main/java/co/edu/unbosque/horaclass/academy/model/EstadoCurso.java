package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ESTADO_CURSO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCurso {

    @Id
    @Column(name = "IdEstadoCurso")
    private int idEstadoCurso;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String nombre;
}