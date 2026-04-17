package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SesionResponseDto {
    private int idSesion;
    private int idGrupo;
    private int idAula;
    private int idDia;
    private java.time.LocalTime hora_inicio;
    private java.time.LocalTime hora_fin;
}