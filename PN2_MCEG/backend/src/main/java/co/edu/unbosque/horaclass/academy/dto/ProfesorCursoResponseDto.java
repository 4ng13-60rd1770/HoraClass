package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfesorCursoResponseDto {
    private int idEmpleado;
    private int idCurso;
}