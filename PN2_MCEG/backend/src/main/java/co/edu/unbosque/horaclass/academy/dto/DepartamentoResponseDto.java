package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartamentoResponseDto {
    private int idDepartamento;
    private String nombre;
}