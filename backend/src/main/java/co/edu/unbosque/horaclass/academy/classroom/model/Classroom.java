package co.edu.unbosque.horaclass.academy.classroom.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SALON")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdSalon")
    private Integer idSalon;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "Edificio", length = 100)
    private String edificio;

    @Column(name = "Capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "Tipo", length = 50)
    private String tipo;

    @Column(name = "Departamento", length = 100)
    private String departamento;

    @Column(name = "Disponible", nullable = false)
    private Boolean disponible = true;
}
