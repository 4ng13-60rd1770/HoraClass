export interface TeacherResponse {
  idProfesor: number;
  nombre: string;
  username: string;
  departamento: string;
  especialidad: string;
  cargaHoras: number;
  escalafon: string;
  tipoVinculacion: string;
  restriccionHorario: string;
  cursosHabilitados?: number[];
}

export interface TeacherRequest {
  idProfesor: number;
  primerNombre: string;
  primerApellido: string;
  username: string;
  carrera: string;
  cargaHoras: number;
  escalafon?: string;
  tipoVinculacion?: string;
  restriccionHorario?: string;
  cursosHabilitados?: number[];
}
