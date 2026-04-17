package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dia {

    @Id
    @Column(name = "IdDia")
    private int idDia;

    @Column(name = "Nombre", length = 20, nullable = false)
    private String nombre;
}