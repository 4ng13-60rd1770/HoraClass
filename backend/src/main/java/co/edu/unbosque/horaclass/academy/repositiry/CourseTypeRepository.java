package co.edu.unbosque.horaclass.academy.repositiry;

import co.edu.unbosque.horaclass.academy.model.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseTypeRepository extends JpaRepository<CourseType, Integer> {
}
