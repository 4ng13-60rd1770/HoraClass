package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ESTUDIO_EMPLEADO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudioEmpleado {

    @Id
    @Column(name = "IdEstudio")
    private int idEstudio;

    @Column(name = "IdEmpleado", nullable = false)
    private int idEmpleado;

    @Column(name = "IdNivel", nullable = false)
    private int idNivel;

    @Column(name = "IdArea", nullable = false)
    private int idArea;

    @Column(name = "IdInstitucion", nullable = false)
    private int idInstitucion;

    @Column(name = "Titulo", length = 200, nullable = false)
    private String titulo;

    @Column(name = "Anio_grado")
    private Integer anio_grado;
}