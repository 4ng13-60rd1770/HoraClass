import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, catchError, of, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  ScheduleResponse,
} from '../models/schedule.models';
import { DEFAULT_SCHEDULING_RULES, SchedulingRules } from '../models/scheduling.models';

@Injectable({ providedIn: 'root' })
export class ScheduleService {
  private horarioUrl = `${environment.apiUrl}/horarios`;
  private reglasUrl = `${environment.apiUrl}/admin/scheduling/reglas`;

  private reglasSubject = new BehaviorSubject<SchedulingRules>(DEFAULT_SCHEDULING_RULES);
  reglas$ = this.reglasSubject.asObservable();

  constructor(private http: HttpClient) {}

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

  getReglas(): Observable<SchedulingRules> {
    return this.http.get<SchedulingRules>(this.reglasUrl).pipe(
      catchError(() => of(DEFAULT_SCHEDULING_RULES)),
      tap(r => this.reglasSubject.next(r)),
    );
  }

  updateReglas(reglas: SchedulingRules): Observable<SchedulingRules> {
    return this.http.put<SchedulingRules>(this.reglasUrl, reglas).pipe(
      tap(r => this.reglasSubject.next(r)),
    );
  }

  restablecerReglas(): Observable<SchedulingRules> {
    return this.http.post<SchedulingRules>(`${this.reglasUrl}/restablecer`, {}).pipe(
      tap(r => this.reglasSubject.next(r)),
    );
  }
}
