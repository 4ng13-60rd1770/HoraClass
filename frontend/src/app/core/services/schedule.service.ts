import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  TimeSlotRequest,
  TimeSlotResponse,
  ScheduleResponse,
} from '../models/schedule.models';

@Injectable({ providedIn: 'root' })
export class ScheduleService {
  private horarioUrl = `${environment.apiUrl}/horarios`;
  private franjaUrl = `${environment.apiUrl}/franjas`;

  constructor(private http: HttpClient) {}

  // ========== Franjas Horarias ==========

  getFranjas(): Observable<TimeSlotResponse[]> {
    return this.http.get<TimeSlotResponse[]>(this.franjaUrl);
  }

  getFranjasDisponibles(): Observable<TimeSlotResponse[]> {
    return this.http.get<TimeSlotResponse[]>(`${this.franjaUrl}/disponibles`);
  }

  getFranja(id: number): Observable<TimeSlotResponse> {
    return this.http.get<TimeSlotResponse>(`${this.franjaUrl}/${id}`);
  }

  crearFranja(franja: TimeSlotRequest): Observable<TimeSlotResponse> {
    return this.http.post<TimeSlotResponse>(this.franjaUrl, franja);
  }

  actualizarFranja(id: number, franja: TimeSlotRequest): Observable<TimeSlotResponse> {
    return this.http.put<TimeSlotResponse>(`${this.franjaUrl}/${id}`, franja);
  }

  eliminarFranja(id: number): Observable<void> {
    return this.http.delete<void>(`${this.franjaUrl}/${id}`);
  }

  // ========== Horarios ==========

  generarHorario(semestre: string, variantes = 1): Observable<ScheduleResponse> {
    return this.http.post<ScheduleResponse>(
      `${this.horarioUrl}/generar/${semestre}?variantes=${variantes}`,
      {}
    );
  }

  obtenerHorario(semestre: string): Observable<ScheduleResponse> {
    return this.http.get<ScheduleResponse>(`${this.horarioUrl}/${semestre}`);
  }

  listarHorarios(): Observable<ScheduleResponse[]> {
    return this.http.get<ScheduleResponse[]>(this.horarioUrl);
  }

  publicarHorario(semestre: string): Observable<ScheduleResponse> {
    return this.http.patch<ScheduleResponse>(
      `${this.horarioUrl}/${semestre}/publicar`,
      {}
    );
  }

  eliminarHorario(semestre: string): Observable<void> {
    return this.http.delete<void>(`${this.horarioUrl}/${semestre}`);
  }
}
