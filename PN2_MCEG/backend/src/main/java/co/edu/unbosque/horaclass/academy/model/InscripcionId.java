package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionId implements Serializable {
    @Column(name = "IdEstudiante")
    private int idEstudiante;
    @Column(name = "IdGrupo")
    private int idGrupo;
}
