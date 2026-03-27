package co.edu.unbosque.horaclass.user.model;

import co.edu.unbosque.horaclass.academy.teacher.model.Teacher;
import co.edu.unbosque.horaclass.academy.teacher.repository.TeacherRepository;
import co.edu.unbosque.horaclass.user.repository.UserRepository;
import org.hibernate.boot.query.HbmResultSetMappingDescriptor;
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
    CommandLineRunner initDatabase(UserRepository userRepository, TeacherRepository teacherRepository,
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
            if (teacherRepository.count()==0){
                // Crear estado ACTIVO si no existe (o asumimos que ya está en BD)
                State estadoActivo = new State(1, "ACTIVO");
                User usuario = new User();
                usuario.setIdUsuario(22222222L);
                usuario.setPrimerNombre("JUAN");
                usuario.setPrimerApellido("PEREZ");
                usuario.setUsername("jperez");
                usuario.setPassword(passwordEncoder.encode("user123"));
                usuario.setRol("PROFESOR");
                usuario.setEstado(estadoActivo);
                userRepository.save(usuario);
                Teacher profesor = new Teacher();
                //profesor.setIdProfesor(22222222L);  // ← Mismo ID
                profesor.setUsuario(usuario);        // ← Relación 1:1
                profesor.setDepartamento("Sistemas");
                profesor.setCargaHoras(12);
                profesor.setEscalafon("JIJOJIJA");
                teacherRepository.save(profesor);
                System.out.println("✅ Profesor administrador creado por defecto");
                System.out.println("   Username: admin");
                System.out.println("   Password: admin123");
            }
        };
    }
}
