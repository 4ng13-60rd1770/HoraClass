package co.edu.unbosque.horaclass.schedule.generation.model;

import co.edu.unbosque.horaclass.academy.classroom.model.Classroom;
import co.edu.unbosque.horaclass.academy.group.model.Group;
import co.edu.unbosque.horaclass.academy.teacher.model.Teacher;
import co.edu.unbosque.horaclass.schedule.timeslot.model.TimeSlot;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HORARIO_ENTRADA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEntrada")
    private Long idEntrada;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdGrupo", referencedColumnName = "IdGrupo")
    private Group grupo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdFranja", referencedColumnName = "IdFranja")
    private TimeSlot franja;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdProfesor", referencedColumnName = "IdProfesor")
    private Teacher docente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdSalon", referencedColumnName = "IdSalon")
    private Classroom aula;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdHorario", referencedColumnName = "IdHorario")
    private Schedule schedule;

    @Column(name = "Estado", length = 30)
    private String estado; // ACTIVO, PENDIENTE
}
