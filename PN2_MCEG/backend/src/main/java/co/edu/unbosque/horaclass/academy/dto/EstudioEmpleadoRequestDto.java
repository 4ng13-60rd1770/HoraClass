package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudioEmpleadoRequestDto {
    private int idEstudio;
    private int idEmpleado;
    private int idNivel;
    private int idArea;
    private int idInstitucion;
    private String titulo;
    private Integer anio_grado;
}
