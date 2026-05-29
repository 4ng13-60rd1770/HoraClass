package co.edu.unbosque.horaclass.academy.teacher.model;
import co.edu.unbosque.horaclass.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PROFESOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    @Column(name = "IdProfesor")
    private Long idProfesor;  // ← Mismo valor que Usuario.idUsuario

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // ← CRÍTICO: Usa el mismo ID que la entidad referenciada
    @JoinColumn(name = "IdProfesor", referencedColumnName = "idUsuario")
    private User usuario;  // ← Relación 1:1 con USUARIO

    @Column(name = "Departamento", length = 100)
    private String departamento;

    @Column(name = "Especialidad", length = 100)
    private String especialidad;

    @Column(name = "CargaHoras", nullable = false)
    private Integer cargaHoras;  // Horas máximas por semana

    @Column(name = "Escalafon", length = 50)
    private String escalafon;  // AUXILIAR, ASISTENTE, ASOCIADO, TITULAR

    /** TIEMPO_COMPLETO, TRES_CUARTOS, MEDIO_TIEMPO, CUARTO_TIEMPO */
    @Column(name = "TipoVinculacion", length = 30)
    private String tipoVinculacion;

    /** SIN_RESTRICCION, SOLO_MANANA, DESPUES_16, DESPUES_18 */
    @Column(name = "RestriccionHorario", length = 30)
    private String restriccionHorario;
}
