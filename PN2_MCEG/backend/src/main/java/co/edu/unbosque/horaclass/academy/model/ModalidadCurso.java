package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MODALIDAD_CURSO")
@Data @NoArgsConstructor @AllArgsConstructor
public class ModalidadCurso {

    @Id
    @Column(name = "IdModalidadCurso")
    private int idModalidadCurso;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;
}