package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoMatriculaRequestDto {
    private int idTipoMatricula;
    private String nombre;
}