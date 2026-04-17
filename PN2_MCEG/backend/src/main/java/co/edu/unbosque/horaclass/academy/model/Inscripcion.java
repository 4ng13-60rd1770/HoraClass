package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "INSCRIPCION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @EmbeddedId
    private InscripcionId id;

    @ManyToOne
    @MapsId("idEstudiante")
    @JoinColumn(name = "IdEstudiante")
    private Estudiante estudiante;

    @ManyToOne
    @MapsId("idGrupo")
    @JoinColumn(name = "IdGrupo")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "IdTipoMatricula", nullable = false)
    private TipoMatricula tipoMatricula;

    @ManyToOne
    @JoinColumn(name = "IdEstadoInscripcion", nullable = false)
    private EstadoInscripcion estadoInscripcion;

    @Column(name = "Fecha_inscripcion", nullable = false)
    private LocalDate fechaInscripcion;

    @Column(name = "Comentario", length = 255)
    private String comentario;
}