package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PREGRADO")
@Data @NoArgsConstructor @AllArgsConstructor
public class Pregrado {

    @Id
    @Column(name = "IdPregrado")
    private int idPregrado;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "IdDepartamento", nullable = false)
    private int idDepartamento;
}