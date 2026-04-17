package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EDIFICIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edificio {

    @Id
    @Column(name = "IdEdificio")
    private int idEdificio;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;
}