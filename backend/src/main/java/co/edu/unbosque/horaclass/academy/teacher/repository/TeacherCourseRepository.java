package co.edu.unbosque.horaclass.academy.teacher.repository;

import co.edu.unbosque.horaclass.academy.teacher.model.TeacherCourse;
import co.edu.unbosque.horaclass.academy.teacher.model.TeacherCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, TeacherCourseId> {

    List<TeacherCourse> findByIdProfesor(Long idProfesor);

    void deleteByIdProfesor(Long idProfesor);

    boolean existsByIdProfesorAndIdCurso(Long idProfesor, Integer idCurso);
}
