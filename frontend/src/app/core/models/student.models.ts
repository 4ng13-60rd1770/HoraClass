export interface StudentResponse {
  idEstudiante: number;
  nombre: string;
  username: string;
  carrera: string;
  semestre: number;
  creditos: number;
  estado: string;
}

export interface StudentRequest {
  idEstudiante: number;
  primerNombre: string;
  primerApellido: string;
  username: string;
  carrera: string;
  semestre: number;
  creditos: number;
}
