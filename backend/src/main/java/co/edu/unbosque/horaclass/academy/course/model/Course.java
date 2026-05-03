package co.edu.unbosque.horaclass.academy.course.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CURSO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {

    @Id
    @Column(name="idCurso")
    private int idCurso;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name="semestre")
    private int semestre;

    @Column(name="creditos")
    private int creditos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idModalidad")
    private Mode idModalidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTipoCurso")
    private CourseType idTipoCurso;
}
