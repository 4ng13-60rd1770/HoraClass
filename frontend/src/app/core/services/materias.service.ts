import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface MateriaShared {
  id: number;
  nombre: string;
  carrera: string;
  docente: string;
  cupoMax: number;
  inscritos: number;
  horario: string;
  salonId?: number | null;
  salonNombre?: string;
  codigo?: string;
  tipo?: string;
  modalidad?: string;
}

@Injectable({ providedIn: 'root' })
export class MateriasService {
  private _nextId = 1;
  private _subject = new BehaviorSubject<MateriaShared[]>([]);

  /** Observable — los componentes se suscriben a este para actualizarse en tiempo real */
  materias$ = this._subject.asObservable();

  /** Valor actual sincrónico */
  get materias(): MateriaShared[] {
    return this._subject.getValue();
  }

  add(data: Omit<MateriaShared, 'id' | 'inscritos' | 'docente'>): MateriaShared {
    const m: MateriaShared = { ...data, id: this._nextId++, inscritos: 0, docente: '' };
    this._subject.next([...this.materias, m]);
    return m;
  }

  update(id: number, data: Partial<MateriaShared>): void {
    this._subject.next(
      this.materias.map(m => m.id === id ? { ...m, ...data } : m)
    );
  }

  remove(id: number): void {
    this._subject.next(this.materias.filter(m => m.id !== id));
  }

  asignarDocente(id: number, nombre: string): void {
    this.update(id, { docente: nombre });
  }

  liberarDocente(id: number): void {
    this.update(id, { docente: '' });
  }

  inscribirEstudiante(id: number): boolean {
    const m = this.materias.find(x => x.id === id);
    if (!m || m.inscritos >= m.cupoMax) return false;
    this.update(id, { inscritos: m.inscritos + 1 });
    return true;
  }

  desinscribirEstudiante(id: number): void {
    const m = this.materias.find(x => x.id === id);
    if (m && m.inscritos > 0) this.update(id, { inscritos: m.inscritos - 1 });
  }
}
