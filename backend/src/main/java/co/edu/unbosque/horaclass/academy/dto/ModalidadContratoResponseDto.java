package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModalidadContratoResponseDto {
    private int idModalidad;
    private String nombre;
}