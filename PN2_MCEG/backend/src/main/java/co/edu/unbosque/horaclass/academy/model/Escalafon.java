package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ESCALAFON")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Escalafon {

    @Id
    @Column(name = "IdEscalafon")
    private int idEscalafon;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String nombre;
}