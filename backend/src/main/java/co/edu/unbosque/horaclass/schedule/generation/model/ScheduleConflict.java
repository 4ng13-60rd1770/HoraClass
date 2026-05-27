package co.edu.unbosque.horaclass.schedule.generation.model;

import co.edu.unbosque.horaclass.academy.group.model.Group;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "HORARIO_CONFLICTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleConflict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdConflicto")
    private Long idConflicto;

    @Enumerated(EnumType.STRING)
    @Column(name = "Motivo", length = 50, nullable = false)
    private ConflictReason motivo;

    @Column(name = "Descripcion", length = 500)
    private String descripcion;

    @Column(name = "Estado", length = 30, nullable = false)
    private String estado; // PENDIENTE, RESUELTO

    @Column(name = "FechaDeteccion")
    private LocalDateTime fechaDeteccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdGrupo", referencedColumnName = "IdGrupo")
    private Group grupo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdHorario", referencedColumnName = "IdHorario")
    private Schedule schedule;
}
