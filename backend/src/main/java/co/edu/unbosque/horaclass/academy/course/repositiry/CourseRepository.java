package co.edu.unbosque.horaclass.academy.course.repositiry;

import co.edu.unbosque.horaclass.academy.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findCourseByNombre(String nombre);
    boolean existsCourseByNombre(String nombre);
    boolean existsCourseByIdCurso(Integer idCurso);
}
