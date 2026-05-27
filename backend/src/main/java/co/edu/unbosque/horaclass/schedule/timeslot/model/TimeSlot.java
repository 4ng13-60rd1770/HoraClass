package co.edu.unbosque.horaclass.schedule.timeslot.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FRANJA_HORARIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdFranja")
    private Long idFranja;

    @Column(name = "DiaSemana", length = 20, nullable = false)
    private String diaSemana; // LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO

    @Column(name = "HoraInicio", length = 10, nullable = false)
    private String horaInicio; // "07:00"

    @Column(name = "HoraFin", length = 10, nullable = false)
    private String horaFin; // "09:00"

    @Column(name = "Turno", length = 20, nullable = false)
    private String turno; // MANANA, TARDE, NOCHE

    @Column(name = "Bloqueado", nullable = false)
    private Boolean bloqueado = false;
}
