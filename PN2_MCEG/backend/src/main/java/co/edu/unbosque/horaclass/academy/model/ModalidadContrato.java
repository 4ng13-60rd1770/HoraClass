package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MODALIDAD_CONTRATO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModalidadContrato {

    @Id
    @Column(name = "IdModalidad")
    private int idModalidad;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;

}
