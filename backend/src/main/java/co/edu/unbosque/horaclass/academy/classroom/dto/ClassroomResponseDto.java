package co.edu.unbosque.horaclass.academy.classroom.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassroomResponseDto {
    private Integer idSalon;
    private String nombre;
    private String edificio;
    private Integer capacidad;
    private String tipo;
    private String departamento;
    private Boolean disponible;
    private Boolean tieneComputadores;
    private Boolean sillasMoviles;
}
