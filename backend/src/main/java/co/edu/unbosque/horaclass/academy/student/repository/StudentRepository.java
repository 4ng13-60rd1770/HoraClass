package co.edu.unbosque.horaclass.academy.student.repository;

import co.edu.unbosque.horaclass.academy.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
