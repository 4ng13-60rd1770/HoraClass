package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ESTADO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estado {

    @Id
    @Column(name = "IdEstado")
    private int idEstado;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String nombre;
}