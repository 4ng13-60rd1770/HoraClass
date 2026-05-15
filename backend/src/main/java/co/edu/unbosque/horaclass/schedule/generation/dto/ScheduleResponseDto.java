package co.edu.unbosque.horaclass.schedule.generation.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long idHorario;
    private String semestre;
    private String estado;
    private LocalDate fechaGeneracion;
    private Boolean publicado;
    private int totalEntries;
    private int totalConflictos;
    private List<ScheduleEntryResponseDto> entries;
    private List<ScheduleConflictResponseDto> conflictos;
}
