package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DEPARTAMENTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Departamento {

    @Id
    @Column(name = "IdDepartamento")
    private int idDepartamento;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;
}