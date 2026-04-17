package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModalidadCursoResponseDto {
    private int idModalidadCurso;
    private String nombre;
}