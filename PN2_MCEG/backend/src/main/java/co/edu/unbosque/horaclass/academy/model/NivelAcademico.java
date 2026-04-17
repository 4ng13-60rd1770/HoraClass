package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NIVEL_ACADEMICO")
@Data @NoArgsConstructor @AllArgsConstructor
public class NivelAcademico {
    @Id
    @Column(name = "IdNivel")
    private int idNivel;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;
}