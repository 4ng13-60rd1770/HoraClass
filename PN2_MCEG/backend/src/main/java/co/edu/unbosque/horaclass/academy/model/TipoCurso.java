package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TIPO_CURSO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoCurso {

    @Id
    @Column(name = "IdTipoCurso")
    private int idTipoCurso;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;
}