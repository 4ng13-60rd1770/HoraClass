package co.edu.unbosque.horaclass.user.repository;

import co.edu.unbosque.horaclass.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, String> {

    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByIdUsuario(long idUsuario);

    Optional<User> findByIdUsuario(long idUsuario);

    java.util.List<User> findByRol(String rol);
}
