package co.edu.unbosque.horaclass.schedule.generation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HORARIO_GENERADO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdHorario")
    private Long idHorario;

    @Column(name = "Semestre", length = 20, nullable = false, unique = true)
    private String semestre;

    @Column(name = "Estado", length = 20, nullable = false)
    private String estado; // BORRADOR, PUBLICADO

    @Column(name = "FechaGeneracion", nullable = false)
    private LocalDate fechaGeneracion;

    @Column(name = "Publicado", nullable = false)
    private Boolean publicado = false;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScheduleEntry> entries = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScheduleConflict> conflictos = new ArrayList<>();

    public void agregarEntry(ScheduleEntry entry) {
        this.entries.add(entry);
        entry.setSchedule(this);
    }

    public void agregarConflicto(ScheduleConflict conflicto) {
        this.conflictos.add(conflicto);
        conflicto.setSchedule(this);
    }
}
