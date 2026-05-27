package co.edu.unbosque.horaclass.schedule.generation.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntryResponseDto {
    private Long idEntrada;
    private String idGrupo;
    private String nombreCurso;
    private int semestreCurso;
    private int creditosCurso;
    private String nombreDocente;
    private String especialidadDocente;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String nombreAula;
    private String edificioAula;
    private int capacidadAula;
    private String estado;
}
