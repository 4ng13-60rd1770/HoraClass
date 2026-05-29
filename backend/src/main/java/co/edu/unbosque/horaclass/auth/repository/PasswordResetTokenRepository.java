package co.edu.unbosque.horaclass.auth.repository;

import co.edu.unbosque.horaclass.auth.model.PasswordResetToken;
import co.edu.unbosque.horaclass.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByTokenAndUsedFalse(String token);

    void deleteByUser(User user);
}
