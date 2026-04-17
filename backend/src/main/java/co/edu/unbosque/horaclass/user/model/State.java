package co.edu.unbosque.horaclass.user.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "ESTADO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class State {
    @Id
    @Column(name = "idEstado")
    private int estado;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;
}
