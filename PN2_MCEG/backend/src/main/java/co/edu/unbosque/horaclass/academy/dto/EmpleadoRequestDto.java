package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoRequestDto {
    private int idEmpleado;
    private int idTipoDocumento;
    private String documento;
    private String primer_nombre;
    private String segundo_nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private int idEstado;
}
