package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TIPO_MATRICULA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoMatricula {

    @Id
    @Column(name = "IdTipoMatricula")
    private int idTipoMatricula;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;
}