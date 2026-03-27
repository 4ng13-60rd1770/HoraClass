package co.edu.unbosque.horaclass.academy.course.repositiry;

import co.edu.unbosque.horaclass.academy.course.model.Mode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeRepository extends JpaRepository<Mode, Integer> {

}
