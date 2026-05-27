import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { MateriasService, MateriaShared } from '../../core/services/materias.service';

// ── Tipos ──────────────────────────────────────────────────────────────────
interface Bloque {
  materia:    string;
  docente:    string;
  iniciales:  string;
  salon:      string;
  carrera:    string;
  dia:        number;   // 0=Lun … 4=Vie
  horaInicio: number;   // p.ej. 8  ó  10.5
  horaFin:    number;
  color:      string;
  colorLight: string;
  sinDocente: boolean;
}

interface DocenteInfo {
  nombre:        string;
  iniciales:     string;
  color:         string;
  colorLight:    string;
  carrera:       string;
  horasAsignadas: number;
  horasMax:      number;
}

// ── Constantes ─────────────────────────────────────────────────────────────
const PALETA = [
  { color: '#7c3aed', light: '#ede9fe' },
  { color: '#b45309', light: '#fef3c7' },
  { color: '#0369a1', light: '#e0f2fe' },
  { color: '#be185d', light: '#fce7f3' },
  { color: '#065f46', light: '#d1fae5' },
  { color: '#9a3412', light: '#ffedd5' },
  { color: '#16a34a', light: '#dcfce7' },
  { color: '#2563eb', light: '#dbeafe' },
];

const DIAS_MAP: Record<string, number> = {
  lunes: 0, lun: 0,
  martes: 1, mar: 1,
  miercoles: 2, mie: 2,
  jueves: 3, jue: 3,
  viernes: 4, vie: 4,
};

@Component({
  selector: 'app-calendarios-secretaria',
  standalone: true,
  imports: [CommonModule, FormsModule, TopbarComponent],
  templateUrl: './calendarios-secretaria.component.html',
  styleUrls: ['./calendarios-secretaria.component.scss'],
})
export class CalendariosSecretariaComponent implements OnInit, OnDestroy {

  // ── Estado de vista ───────────────────────────────────────────────────────
  vistaActiva: 'semana' | 'dia' = 'semana';
  diaSeleccionado = new Date().getDay() === 0 || new Date().getDay() === 6
    ? 0 : new Date().getDay() - 1;

  readonly dias  = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes'];
  readonly horas = [7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19];

  readonly diaHoy = (() => {
    const d = new Date().getDay();
    return (d === 0 || d === 6) ? -1 : d - 1;
  })();

  // ── Filtro por carrera ────────────────────────────────────────────────────
  filtroCarrera = 'Todas';
  carreras: string[] = ['Todas'];

  // ── Datos procesados ──────────────────────────────────────────────────────
  allBloques:  Bloque[]      = [];
  allDocentes: DocenteInfo[] = [];

  private sub = new Subscription();

  constructor(private svc: MateriasService, private cdr: ChangeDetectorRef) {}

  // ── Lifecycle ─────────────────────────────────────────────────────────────
  ngOnInit(): void {
    this.sub.add(
      this.svc.materias$.subscribe(list => {
        this.procesarMaterias(list);
        this.cdr.detectChanges();
      }),
    );
  }

  ngOnDestroy(): void { this.sub.unsubscribe(); }

  // ── Procesado de materias → bloques + docentes ────────────────────────────
  private procesarMaterias(list: MateriaShared[]): void {
    // Asignar color fijo por docente
    const colorMap = new Map<string, { color: string; light: string }>();
    let ci = 0;
    list.forEach(m => {
      if (m.docente && !colorMap.has(m.docente)) {
        colorMap.set(m.docente, PALETA[ci % PALETA.length]);
        ci++;
      }
    });

    // Construir bloques (solo materias con horario)
    this.allBloques = [];
    list.forEach(m => {
      if (!m.horario?.trim()) return;
      const colorEntry = m.docente
        ? colorMap.get(m.docente)!
        : { color: '#9ca3af', light: '#f3f4f6' };

      this.parsearHorario(m.horario).forEach(slot => {
        this.allBloques.push({
          materia:    m.nombre,
          docente:    m.docente || 'Sin docente',
          iniciales:  m.docente ? this.getIniciales(m.docente) : '?',
          salon:      m.salonNombre ?? '—',
          carrera:    m.carrera,
          dia:        slot.dia,
          horaInicio: slot.horaInicio,
          horaFin:    slot.horaFin,
          color:      colorEntry.color,
          colorLight: colorEntry.light,
          sinDocente: !m.docente,
        });
      });
    });

    // Construir lista de docentes asignados
    const docentesMap = new Map<string, { materias: MateriaShared[]; color: typeof PALETA[0] }>();
    list.forEach(m => {
      if (!m.docente) return;
      if (!docentesMap.has(m.docente)) {
        docentesMap.set(m.docente, { materias: [], color: colorMap.get(m.docente)! });
      }
      docentesMap.get(m.docente)!.materias.push(m);
    });

    this.allDocentes = [];
    docentesMap.forEach((val, nombre) => {
      let horas = 0;
      val.materias.forEach(m => {
        this.parsearHorario(m.horario ?? '').forEach(s => horas += s.horaFin - s.horaInicio);
      });
      this.allDocentes.push({
        nombre,
        iniciales:      this.getIniciales(nombre),
        color:          val.color.color,
        colorLight:     val.color.light,
        carrera:        val.materias[0]?.carrera ?? '',
        horasAsignadas: Math.round(horas),
        horasMax:       40,
      });
    });

    // Actualizar carreras disponibles para el filtro
    const cars = [...new Set(list.map(m => m.carrera).filter(Boolean))];
    this.carreras = ['Todas', ...cars];
    if (!this.carreras.includes(this.filtroCarrera)) this.filtroCarrera = 'Todas';
  }

  // ── Getters filtrados ──────────────────────────────────────────────────────
  get bloquesFiltrados(): Bloque[] {
    if (this.filtroCarrera === 'Todas') return this.allBloques;
    return this.allBloques.filter(b => b.carrera === this.filtroCarrera);
  }

  get docentesFiltrados(): DocenteInfo[] {
    if (this.filtroCarrera === 'Todas') return this.allDocentes;
    return this.allDocentes.filter(d => d.carrera === this.filtroCarrera);
  }

  // ── Helpers del calendario ─────────────────────────────────────────────────
  bloquesEnCelda(dia: number, hora: number): Bloque[] {
    return this.bloquesFiltrados.filter(
      b => b.dia === dia && hora >= b.horaInicio && hora < b.horaFin,
    );
  }

  esPrimerHora(b: Bloque, hora: number): boolean {
    return Math.floor(b.horaInicio) === hora;
  }

  duracion(b: Bloque): number { return b.horaFin - b.horaInicio; }

  /** % de disponibilidad de ese día (docentes sin bloques ese día) */
  disponibilidadDia(dia: number): number {
    const docs = this.docentesFiltrados;
    if (docs.length === 0) return 100;
    const libres = docs.filter(d =>
      !this.bloquesFiltrados.some(b => b.docente === d.nombre && b.dia === dia),
    ).length;
    return Math.round((libres / docs.length) * 100);
  }

  estaLibre(docente: DocenteInfo, dia: number): boolean {
    return !this.bloquesFiltrados.some(b => b.docente === docente.nombre && b.dia === dia);
  }

  formatHora(h: number): string {
    return `${Math.floor(h).toString().padStart(2, '0')}:00`;
  }

  get totalClasesHoy(): number {
    return this.bloquesFiltrados.filter(b => b.dia === this.diaSeleccionado).length;
  }

  get hayDatos(): boolean { return this.allBloques.length > 0; }

  // ── Utilidades ─────────────────────────────────────────────────────────────
  private getIniciales(username: string): string {
    const parts = username.split(/[.\-_ ]/);
    if (parts.length >= 2) return (parts[0][0] + parts[1][0]).toUpperCase();
    return username.slice(0, 2).toUpperCase();
  }

  private parsearHorario(horario: string): { dia: number; horaInicio: number; horaFin: number }[] {
    if (!horario?.trim()) return [];
    const h = horario.toLowerCase().normalize('NFD').replace(/[̀-ͯ]/g, '');
    const dias: number[] = [];
    for (const [key, val] of Object.entries(DIAS_MAP)) {
      if (h.includes(key) && !dias.includes(val)) dias.push(val);
    }
    const m = h.match(/(\d{1,2})[:\.]?(\d{0,2})\s*[-–]\s*(\d{1,2})[:\.]?(\d{0,2})/);
    if (!m || dias.length === 0) return [];
    const horaInicio = parseInt(m[1]) + (m[2] ? parseInt(m[2] || '0') / 60 : 0);
    const horaFin    = parseInt(m[3]) + (m[4] ? parseInt(m[4] || '0') / 60 : 0);
    if (horaFin <= horaInicio) return [];
    return dias.map(dia => ({ dia, horaInicio, horaFin }));
  }
}
