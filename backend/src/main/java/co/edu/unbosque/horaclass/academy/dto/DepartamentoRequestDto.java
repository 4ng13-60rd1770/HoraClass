package co.edu.unbosque.horaclass.academy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoRequestDto {
    private int idDepartamento;
    private String nombre;
}