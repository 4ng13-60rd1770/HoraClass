package co.edu.unbosque.horaclass.academy.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfesorCursoId implements Serializable {

    private int idEmpleado;
    private int idCurso;
}