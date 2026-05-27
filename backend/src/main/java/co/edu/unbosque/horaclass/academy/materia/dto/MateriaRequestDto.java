package co.edu.unbosque.horaclass.academy.materia.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaRequestDto {
    private String nombre;
    private String carrera;
    private String codigo;
    private String tipo;
    private String modalidad;
    private Integer cupoMax;
    private String horario;
    private Integer salonId;
}
