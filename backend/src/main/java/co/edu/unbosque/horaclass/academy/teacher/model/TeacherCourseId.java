package co.edu.unbosque.horaclass.academy.teacher.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCourseId implements Serializable {
    private Long idProfesor;
    private Integer idCurso;
}
