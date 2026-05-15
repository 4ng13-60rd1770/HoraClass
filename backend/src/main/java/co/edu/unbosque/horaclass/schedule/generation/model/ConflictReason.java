package co.edu.unbosque.horaclass.schedule.generation.model;

public enum ConflictReason {
    SIN_DOCENTE_DISPONIBLE,
    SIN_AULA_DISPONIBLE,
    SIN_FRANJA_DISPONIBLE,
    CARGA_MAXIMA_ALCANZADA,
    SIN_AULA_CON_CAPACIDAD,
    DOCENTE_DUPLICADO,
    AULA_DUPLICADA,
    CRUCE_HORARIO
}
