package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ESTADO_INSCRIPCION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoInscripcion {

    @Id
    @Column(name = "IdEstadoInscripcion")
    private int idEstadoInscripcion;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String nombre;
}