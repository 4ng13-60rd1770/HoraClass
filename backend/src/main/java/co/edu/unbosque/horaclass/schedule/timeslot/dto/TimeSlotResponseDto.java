package co.edu.unbosque.horaclass.schedule.timeslot.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotResponseDto {
    private Long idFranja;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String turno;
    private Boolean bloqueado;
}
