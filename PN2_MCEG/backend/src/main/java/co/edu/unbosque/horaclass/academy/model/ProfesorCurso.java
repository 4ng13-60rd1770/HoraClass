package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PROFESOR_CURSO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfesorCurso {

    @EmbeddedId
    private ProfesorCursoId id;

    // 🔥 RELACIONES (MUY RECOMENDADO)
    @ManyToOne
    @MapsId("idEmpleado")
    @JoinColumn(name = "IdEmpleado")
    private Profesor profesor;

    @ManyToOne
    @MapsId("idCurso")
    @JoinColumn(name = "IdCurso")
    private Curso curso;
}