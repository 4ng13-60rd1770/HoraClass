export interface ClassroomResponse {
  idSalon: number;
  nombre: string;
  edificio: string;
  capacidad: number;
  tipo: string;
  departamento: string;
  disponible: boolean;
}

export interface ClassroomRequest {
  nombre: string;
  edificio: string;
  capacidad: number;
  tipo: string;
  departamento: string;
  disponible: boolean;
}
