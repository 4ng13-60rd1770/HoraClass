package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "INSTITUCION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Institucion {

    @Id
    @Column(name = "IdInstitucion")
    private int idInstitucion;

    @Column(name = "Nombre", length = 200, nullable = false)
    private String nombre;

}
