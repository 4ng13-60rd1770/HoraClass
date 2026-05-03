package co.edu.unbosque.horaclass.user.model;

import co.edu.unbosque.horaclass.user.repository.StateRepository;
import co.edu.unbosque.horaclass.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   StateRepository stateRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            if (!stateRepository.existsById(1)) {
                stateRepository.save(new State(1, "ACTIVO"));
                stateRepository.save(new State(2, "INACTIVO"));
                stateRepository.save(new State(3, "SUSPENDIDO"));
            }
            State estadoActivo = stateRepository.findById(1).orElseThrow();

            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setIdUsuario(1111111111L);
                admin.setPrimerNombre("Administrador");
                admin.setPrimerApellido("Sistema");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRol("ADMIN");
                admin.setEstado(estadoActivo);
                userRepository.save(admin);
                System.out.println("✅ Admin creado — username: admin | password: admin123");
            }

            if (!userRepository.existsByUsername("jperez")) {
                User u = new User();
                u.setIdUsuario(22222222L);
                u.setPrimerNombre("Juan");
                u.setPrimerApellido("Perez");
                u.setUsername("jperez");
                u.setPassword(passwordEncoder.encode("user123"));
                u.setRol("DOCENTE");
                u.setEstado(estadoActivo);
                userRepository.save(u);
                System.out.println("✅ Docente creado — username: jperez | password: user123");
            }

            userRepository.findUserByUsername("mlopez").ifPresentOrElse(u -> {
                if (!u.getPassword().startsWith("$2a$")) {
                    u.setPassword(passwordEncoder.encode("maria123"));
                    userRepository.save(u);
                    System.out.println("✅ Contraseña de mlopez actualizada");
                }
            }, () -> {
                User u = new User();
                u.setIdUsuario(33333333L);
                u.setPrimerNombre("Maria");
                u.setPrimerApellido("Lopez");
                u.setUsername("mlopez");
                u.setPassword(passwordEncoder.encode("maria123"));
                u.setRol("ESTUDIANTE");
                u.setEstado(estadoActivo);
                userRepository.save(u);
                System.out.println("✅ Estudiante creado — username: mlopez | password: maria123");
            });
        };
    }
}
