package co.edu.unbosque.horaclass.academy.course.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "MODALIDAD")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mode {

    @Id
    @Column(name = "idModalidad")
    private int idModalidad;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
}
