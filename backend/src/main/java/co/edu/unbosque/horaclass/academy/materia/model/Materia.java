package co.edu.unbosque.horaclass.academy.materia.model;

import co.edu.unbosque.horaclass.academy.classroom.model.Classroom;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MATERIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdMateria")
    private Long id;

    @Column(name = "Nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "Carrera", length = 150, nullable = false)
    private String carrera;

    @Column(name = "Codigo", length = 50)
    private String codigo;

    @Column(name = "Tipo", length = 50)
    private String tipo;

    @Column(name = "Modalidad", length = 50)
    private String modalidad;

    @Column(name = "CupoMax", nullable = false)
    private Integer cupoMax = 30;

    @Column(name = "Inscritos", nullable = false)
    private Integer inscritos = 0;

    @Column(name = "Horario", length = 200)
    private String horario;

    /** Username del docente asignado (nullable cuando no hay asignación) */
    @Column(name = "Docente", length = 100)
    private String docente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdSalon")
    private Classroom salon;
}
