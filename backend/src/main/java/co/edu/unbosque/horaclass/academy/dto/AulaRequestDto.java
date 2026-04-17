package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AulaRequestDto {
    private int idAula;
    private int idEdificio;
    private int idTipoAula;
    private int idTipoSillas;
    private int capacidad;
    private String nombre;
}
