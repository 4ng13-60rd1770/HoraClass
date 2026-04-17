package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CARGO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cargo {

    @Id
    @Column(name = "IdCargo")
    private int idCargo;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;
}