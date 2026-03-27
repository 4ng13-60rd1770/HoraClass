package co.edu.unbosque.horaclass.academy.teacher.repository;

import co.edu.unbosque.horaclass.academy.teacher.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // Buscar profesor por ID de usuario
    Optional<Teacher> findByUsuarioIdUsuario(Long idUsuario);

    // Verificar si existe un profesor por ID de usuario
    boolean existsByUsuarioIdUsuario(Long idUsuario);

}
