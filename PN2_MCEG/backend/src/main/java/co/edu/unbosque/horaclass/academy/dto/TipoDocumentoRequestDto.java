package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumentoRequestDto {
    private int idTipoDocumento;
    private String nombre;
}
