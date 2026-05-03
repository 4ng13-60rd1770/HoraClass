export interface CourseRequest {
  idCurso: number;
  nombre: string;
  semestre: number;
  creditos: number;
  idModalidad: number;
  idTipoCurso: number;
}

export interface CourseResponse {
  idCurso: number;
  nombre: string;
  semestre: number;
  creditos: number;
  modalidad: string;
  tipoCurso: string;
}

export interface GroupRequest {
  idGrupo: string;
  idCurso: number;
  idProfesor: number;
  cupoMaximo: number;
  cupoMinimo: number;
  idEstadoGrupo: number;
}

export interface GroupResponse {
  idGrupo: string;
  curso: CourseResponse;
  cupoMaximo: number;
  cupoMinimo: number;
  estadoGrupo: { idEstadoGrupo: number; nombre: string };
}
