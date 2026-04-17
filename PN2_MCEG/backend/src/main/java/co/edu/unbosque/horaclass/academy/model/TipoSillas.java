package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TIPO_SILLAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoSillas {

    @Id
    @Column(name = "IdTipoSillas")
    private int idTipoSillas;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;
}