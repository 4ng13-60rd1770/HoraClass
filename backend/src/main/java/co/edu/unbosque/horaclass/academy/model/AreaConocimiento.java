package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AREA_CONOCIMIENTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaConocimiento {

    @Id
    @Column(name = "IdArea")
    private int idArea;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;
}