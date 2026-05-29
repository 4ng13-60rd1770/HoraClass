package co.edu.unbosque.horaclass.academy.teacher.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROFESOR_CURSO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TeacherCourseId.class)
public class TeacherCourse {

    @Id
    @Column(name = "IdProfesor")
    private Long idProfesor;

    @Id
    @Column(name = "IdCurso")
    private Integer idCurso;
}
