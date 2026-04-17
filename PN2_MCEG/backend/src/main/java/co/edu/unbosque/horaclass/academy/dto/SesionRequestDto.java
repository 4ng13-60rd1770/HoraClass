package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SesionRequestDto {
    private int idSesion;
    private int idGrupo;
    private int idAula;
    private int idDia;
    private java.time.LocalTime hora_inicio;
    private java.time.LocalTime hora_fin;
}