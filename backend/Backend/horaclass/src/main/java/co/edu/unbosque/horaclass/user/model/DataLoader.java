package co.edu.unbosque.horaclass.user.model;

import co.edu.unbosque.horaclass.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                // Crear estado ACTIVO si no existe (o asumimos que ya está en BD)
                State estadoActivo = new State(1, "ACTIVO");

                User admin = new User();
                admin.setIdUsuario(1111111111);
                admin.setPrimerNombre("Administrador");
                admin.setSegundoNombre("General");
                admin.setPrimerApellido("del");
                admin.setSegundoApellido("Sistema");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRol("ADMIN");
                admin.setEstado(estadoActivo);

                userRepository.save(admin);
                System.out.println("✅ Usuario administrador creado por defecto");
                System.out.println("   Username: admin");
                System.out.println("   Password: admin123");
            }
        };
    }
}
