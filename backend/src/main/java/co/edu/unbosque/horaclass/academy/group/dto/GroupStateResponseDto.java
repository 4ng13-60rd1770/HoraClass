package co.edu.unbosque.horaclass.academy.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupStateResponseDto {
    private Integer idEstadoGrupo;
    private String nombre;
}
