package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscripcionResponseDto {
    private int idEstudiante;
    private int idGrupo;
    private int idTipoMatricula;
    private int idEstadoInscripcion;
    private java.time.LocalDate fecha_inscripcion;
    private String comentario;
}