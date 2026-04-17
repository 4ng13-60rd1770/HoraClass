package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TIPO_AULA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoAula {

    @Id
    @Column(name = "IdTipoAula")
    private int idTipoAula;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;
}
