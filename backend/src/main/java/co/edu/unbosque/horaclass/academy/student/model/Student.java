package co.edu.unbosque.horaclass.academy.student.model;

import co.edu.unbosque.horaclass.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ESTUDIANTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @Column(name = "IdEstudiante")
    private Long idEstudiante;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "IdEstudiante", referencedColumnName = "idUsuario")
    private User usuario;

    @Column(name = "Carrera", length = 100)
    private String carrera;

    @Column(name = "Semestre")
    private Integer semestre;

    @Column(name = "Creditos")
    private Integer creditos;
}
