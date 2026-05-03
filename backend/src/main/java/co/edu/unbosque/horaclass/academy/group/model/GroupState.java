package co.edu.unbosque.horaclass.academy.group.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ESTADO_GRUPO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupState {

    @Id
    @Column(name = "IdEstadoGrupo")
    private Integer idEstadoGrupo;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String nombre;  // ACTIVO, CERRADO, CANCELADO
}
