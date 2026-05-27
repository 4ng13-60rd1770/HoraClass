package co.edu.unbosque.horaclass.schedule.generation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleConflictResponseDto {
    private Long idConflicto;
    private String idGrupo;
    private String nombreCurso;
    private String motivo;
    private String descripcion;
    private String estado;
    private LocalDateTime fechaDeteccion;
}
