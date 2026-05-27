import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface MateriaShared {
  id:          number;
  nombre:      string;
  carrera:     string;
  docente:     string;
  cupoMax:     number;
  inscritos:   number;
  horario:     string;
  salonId?:    number | null;
  salonNombre?: string;
  codigo?:     string;
  tipo?:       string;
  modalidad?:  string;
}

@Injectable({ providedIn: 'root' })
export class MateriasService {

  private readonly adminUrl  = `${environment.apiUrl}/admin/materias`;
  private readonly pubUrl    = `${environment.apiUrl}/materias`;

  /** BehaviorSubject local — se sincroniza con el backend al cargar y tras cada mutación */
  private _subject = new BehaviorSubject<MateriaShared[]>([]);
  materias$ = this._subject.asObservable();

  get materias(): MateriaShared[] { return this._subject.getValue(); }

  constructor(private http: HttpClient) {
    // Carga inicial desde el endpoint público (accesible para todos los roles)
    this.recargar();
  }

  /** Recarga desde el endpoint público — funciona para admin, docente y estudiante */
  recargar(): void {
    this.http.get<MateriaShared[]>(this.pubUrl).subscribe({
      next:  list => this._subject.next(list),
      error: ()   => {}
    });
  }

  /** @deprecated Alias de recargar() */
  cargarTodas():  void { this.recargar(); }
  cargarPublico(): void { this.recargar(); }

  // ── CRUD (ADMIN) ──────────────────────────────────────────────────────────

  add(data: Omit<MateriaShared, 'id' | 'inscritos' | 'docente'>): Observable<MateriaShared> {
    return this.http.post<MateriaShared>(this.adminUrl, data).pipe(
      tap(() => this.recargar())
    );
  }

  update(id: number, data: Partial<MateriaShared>): Observable<MateriaShared> {
    return this.http.put<MateriaShared>(`${this.adminUrl}/${id}`, data).pipe(
      tap(() => this.recargar())
    );
  }

  remove(id: number): Observable<void> {
    return this.http.delete<void>(`${this.adminUrl}/${id}`).pipe(
      tap(() => this.recargar())
    );
  }

  // ── Asignación de docente ─────────────────────────────────────────────────

  /** Docente autenticado se asigna a sí mismo */
  asignarDocente(id: number): Observable<MateriaShared> {
    return this.http.patch<MateriaShared>(
      `${this.pubUrl}/${id}/inscribir-docente`, {}
    ).pipe(tap(() => this.cargarPublico()));
  }

  /** Docente autenticado se desasigna */
  liberarDocente(id: number): Observable<MateriaShared> {
    return this.http.patch<MateriaShared>(
      `${this.pubUrl}/${id}/desinscribir-docente`, {}
    ).pipe(tap(() => this.cargarPublico()));
  }

  // ── Compatibilidad con código existente ───────────────────────────────────
  /** @deprecated Usar asignarDocente() que persiste en BD */
  asignarDocenteLocal(id: number, nombre: string): void {
    this._subject.next(
      this.materias.map(m => m.id === id ? { ...m, docente: nombre } : m)
    );
  }

  /** @deprecated Usar liberarDocente() que persiste en BD */
  liberarDocenteLocal(id: number): void {
    this._subject.next(
      this.materias.map(m => m.id === id ? { ...m, docente: '' } : m)
    );
  }

  inscribirEstudiante(id: number): boolean {
    const m = this.materias.find(x => x.id === id);
    if (!m || m.inscritos >= m.cupoMax) return false;
    this._subject.next(
      this.materias.map(x => x.id === id ? { ...x, inscritos: x.inscritos + 1 } : x)
    );
    return true;
  }

  desinscribirEstudiante(id: number): void {
    const m = this.materias.find(x => x.id === id);
    if (m && m.inscritos > 0) {
      this._subject.next(
        this.materias.map(x => x.id === id ? { ...x, inscritos: x.inscritos - 1 } : x)
      );
    }
  }
}
