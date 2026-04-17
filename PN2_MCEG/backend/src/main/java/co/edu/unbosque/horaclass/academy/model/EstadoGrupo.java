package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ESTADO_GRUPO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoGrupo {

    @Id
    @Column(name = "IdEstadoGrupo")
    private int idEstadoGrupo;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String nombre;
}