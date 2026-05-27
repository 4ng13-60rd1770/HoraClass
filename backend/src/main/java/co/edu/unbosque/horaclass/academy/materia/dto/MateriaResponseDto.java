package co.edu.unbosque.horaclass.academy.materia.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaResponseDto {
    private Long id;
    private String nombre;
    private String carrera;
    private String codigo;
    private String tipo;
    private String modalidad;
    private Integer cupoMax;
    private Integer inscritos;
    private String horario;
    private String docente;
    private Integer salonId;
    private String salonNombre;
}
