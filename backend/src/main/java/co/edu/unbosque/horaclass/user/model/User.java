package co.edu.unbosque.horaclass.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @Column(name = "idUsuario", length = 20)
    private long idUsuario;

    @Column(name = "primerNombre", length = 50, nullable = false)
    private String primerNombre;

    @Column(name = "segundoNombre", length = 50)
    private String segundoNombre;

    @Column(name = "primerApellido", length = 50, nullable = false)
    private String primerApellido;

    @Column(name = "segundoApellido", length = 50)
    private String segundoApellido;

    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "rol", length = 50, nullable = false)
    private String rol;  // 'ADMIN', 'DOCENTE', 'ESTUDIANTE'

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEstado", referencedColumnName = "idEstado")
    private State estado;
}
