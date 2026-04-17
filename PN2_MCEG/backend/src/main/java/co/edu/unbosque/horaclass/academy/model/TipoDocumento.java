package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TIPO_DOCUMENTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumento {

    @Id
    @Column(name = "IdTipoDocumento")
    private int idTipoDocumento;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String nombre;
}
