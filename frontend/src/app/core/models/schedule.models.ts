// ========== TimeSlot (Franja Horaria) ==========

export interface TimeSlotRequest {
  diaSemana: string;
  horaInicio: string;
  horaFin: string;
  turno: string;
  bloqueado: boolean;
}

export interface TimeSlotResponse {
  idFranja: number;
  diaSemana: string;
  horaInicio: string;
  horaFin: string;
  turno: string;
  bloqueado: boolean;
}

// ========== Schedule (Horario Generado) ==========

export interface ScheduleResponse {
  idHorario: number;
  semestre: string;
  estado: string;
  fechaGeneracion: string;
  publicado: boolean;
  totalEntries: number;
  totalConflictos: number;
  entries: ScheduleEntryResponse[];
  conflictos: ScheduleConflictResponse[];
  alertas?: string[];
}

export interface ScheduleEntryResponse {
  idEntrada: number;
  idGrupo: string;
  nombreCurso: string;
  semestreCurso: number;
  creditosCurso: number;
  nombreDocente: string;
  especialidadDocente: string;
  diaSemana: string;
  horaInicio: string;
  horaFin: string;
  nombreAula: string;
  edificioAula: string;
  capacidadAula: number;
  estado: string;
}

export interface ScheduleConflictResponse {
  idConflicto: number;
  idGrupo: string;
  nombreCurso: string;
  motivo: string;
  descripcion: string;
  estado: string;
  fechaDeteccion: string;
}
