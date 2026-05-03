package co.edu.unbosque.horaclass.academy.group.model;

import co.edu.unbosque.horaclass.academy.course.model.Course;
import co.edu.unbosque.horaclass.academy.teacher.model.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GRUPO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @Column(name = "IdGrupo")
    private String idGrupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdCurso", referencedColumnName = "IdCurso")
    private Course curso;  // Relación con CURSO (del módulo cursos)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdProfesor", referencedColumnName = "IdProfesor")
    private Teacher profesor;  // Relación con PROFESOR

    @Column(name = "CupoMaximo", nullable = false)
    private int cupoMaximo;

    @Column(name = "CupoMinimo", nullable = false)
    private int cupoMinimo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdEstadoGrupo", referencedColumnName = "IdEstadoGrupo")
    private GroupState estadoGrupo;  // Relación con ESTADO_GRUPO
}
