package co.edu.unbosque.horaclass.auth.service;

import co.edu.unbosque.horaclass.auth.dto.MessageResponseDto;
import co.edu.unbosque.horaclass.auth.model.PasswordResetToken;
import co.edu.unbosque.horaclass.auth.repository.PasswordResetTokenRepository;
import co.edu.unbosque.horaclass.user.model.User;
import co.edu.unbosque.horaclass.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private static final String GENERIC_MESSAGE =
            "Si el usuario existe, recibirás un correo con instrucciones para restablecer tu contraseña.";

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${app.frontend-url:http://localhost:4200}")
    private String frontendUrl;

    public PasswordResetService(UserRepository userRepository,
                                PasswordResetTokenRepository tokenRepository,
                                PasswordEncoder passwordEncoder,
                                EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public MessageResponseDto requestPasswordReset(String username) {
        userRepository.findUserByUsername(username.trim()).ifPresent(user -> {
            tokenRepository.deleteByUser(user);

            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setUser(user);
            resetToken.setToken(UUID.randomUUID().toString());
            resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
            tokenRepository.save(resetToken);

            String resetLink = frontendUrl + "/restablecer-contrasena?token=" + resetToken.getToken();
            emailService.sendPasswordResetEmail(resolveEmail(user), resetLink, user.getUsername());
        });

        return new MessageResponseDto(GENERIC_MESSAGE);
    }

    @Transactional
    public MessageResponseDto resetPassword(String token, String newPassword) {
        if (newPassword == null || newPassword.trim().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }

        PasswordResetToken resetToken = tokenRepository.findByTokenAndUsedFalse(token)
                .orElseThrow(() -> new IllegalArgumentException("El enlace de recuperación no es válido o ya fue usado."));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("El enlace de recuperación ha expirado. Solicita uno nuevo.");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword.trim()));
        userRepository.save(user);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);

        return new MessageResponseDto("Contraseña actualizada correctamente. Ya puedes iniciar sesión.");
    }

    private String resolveEmail(User user) {
        String username = user.getUsername();
        if (username.contains("@")) {
            return username;
        }
        return username + "@unbosque.edu.co";
    }
}
