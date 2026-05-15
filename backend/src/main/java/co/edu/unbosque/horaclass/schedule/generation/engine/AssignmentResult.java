package co.edu.unbosque.horaclass.schedule.generation.engine;

import co.edu.unbosque.horaclass.academy.classroom.model.Classroom;
import co.edu.unbosque.horaclass.academy.teacher.model.Teacher;
import co.edu.unbosque.horaclass.schedule.generation.model.ConflictReason;
import co.edu.unbosque.horaclass.schedule.timeslot.model.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Resultado de un intento de asignación de un grupo a franja+docente+aula.
 * Adaptado de ResultadoAsignacion del Proyecto_Nasly.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResult {

    private boolean exitoso;
    private Teacher docente;
    private TimeSlot franja;
    private Classroom aula;
    private ConflictReason motivo;
    private String descripcion;
    private boolean fueraEspecialidad;

    public static AssignmentResult exito(Teacher docente, TimeSlot franja, Classroom aula) {
        AssignmentResult r = new AssignmentResult();
        r.setExitoso(true);
        r.setDocente(docente);
        r.setFranja(franja);
        r.setAula(aula);
        return r;
    }

    public static AssignmentResult fallo(ConflictReason motivo, String descripcion) {
        AssignmentResult r = new AssignmentResult();
        r.setExitoso(false);
        r.setMotivo(motivo);
        r.setDescripcion(descripcion);
        return r;
    }

    public static AssignmentResult sinResultado() {
        AssignmentResult r = new AssignmentResult();
        r.setExitoso(false);
        return r;
    }
}
