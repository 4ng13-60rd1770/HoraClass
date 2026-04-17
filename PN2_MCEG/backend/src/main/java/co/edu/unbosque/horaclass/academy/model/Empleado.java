package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EMPLEADO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @Column(name = "IdEmpleado")
    private int idEmpleado;

    @Column(name = "IdTipoDocumento", nullable = false)
    private int idTipoDocumento;

    @Column(name = "Documento", length = 20, nullable = false)
    private String documento;

    @Column(name = "Primer_nombre", length = 50, nullable = false)
    private String primer_nombre;

    @Column(name = "Segundo_nombre", length = 50)
    private String segundo_nombre;

    @Column(name = "Primer_apellido", length = 50, nullable = false)
    private String primer_apellido;

    @Column(name = "Segundo_apellido", length = 50)
    private String segundo_apellido;

    @Column(name = "IdEstado", nullable = false)
    private int idEstado;
}