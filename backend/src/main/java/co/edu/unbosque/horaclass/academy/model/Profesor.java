package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PROFESOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profesor {

    @Id
    @Column(name = "IdEmpleado")
    private int idEmpleado;

    @Column(name = "IdModalidad", nullable = false)
    private int idModalidad;

    @Column(name = "IdEscalafon", nullable = false)
    private int idEscalafon;

    @Column(name = "Carga_horas", nullable = false)
    private int carga_horas;
}