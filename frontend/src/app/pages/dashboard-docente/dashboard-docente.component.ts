import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { StatsRowComponent, StatData } from '../../organisms/stats-row/stats-row.component';
import { MateriasService, MateriaShared } from '../../core/services/materias.service';
import { AuthService } from '../../core/services/auth.service';

// ── Tipos ──────────────────────────────────────────────────────────────────
export interface CalendarioBloque {
  materia: MateriaShared;
  dia: number;        // 0 = Lunes … 4 = Viernes
  horaInicio: number; // p.ej. 8 ó 10.5
  horaFin: number;
  color: string;
  colorLight: string;
}

// ── Paleta de colores para materias ────────────────────────────────────────
const PALETA = [
  { color: '#16a34a', light: '#dcfce7' },
  { color: '#2563eb', light: '#dbeafe' },
  { color: '#7c3aed', light: '#ede9fe' },
  { color: '#d97706', light: '#fef3c7' },
  { color: '#db2777', light: '#fce7f3' },
  { color: '#0891b2', light: '#cffafe' },
];

// ── Mapa de días (español normalizado → índice 0-4) ────────────────────────
const DIAS_MAP: Record<string, number> = {
  lunes: 0, lun: 0,
  martes: 1, mar: 1,
  miercoles: 2, mie: 2,
  jueves: 3, jue: 3,
  viernes: 4, vie: 4,
};

const BASE_HORA = 7; // primera hora visible en el grid
const PX_POR_HORA = 60;

@Component({
  selector: 'app-dashboard-docente',
  standalone: true,
  imports: [CommonModule, TopbarComponent, StatsRowComponent],
  templateUrl: './dashboard-docente.component.html',
  styleUrls: ['./dashboard-docente.component.scss'],
})
export class DashboardDocenteComponent implements OnInit, OnDestroy {

  username = '';
  private sub = new Subscription();

  misMaterias: MateriaShared[] = [];
  bloques: CalendarioBloque[] = [];

  readonly dias   = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes'];
  readonly horas  = [7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19];

  /** Índice del día de hoy (0=Lun…4=Vie, -1 si es fin de semana) */
  readonly diaHoy = (() => {
    const d = new Date().getDay(); // 0=Dom, 1=Lun…
    return d === 0 || d === 6 ? -1 : d - 1;
  })();

  readonly hoyNombre: string;
  readonly fechaHoy: string;

  stats: StatData[] = [
    { label: 'Materias Asignadas', value: '0', subtitle: 'Este semestre', hasDonut: true, donutPercent: 0 },
    { label: 'Horas Semanales',    value: '0', subtitle: 'Total en horario' },
    { label: 'Clases Hoy',         value: '0', subtitle: 'Programadas para hoy' },
  ];

  constructor(
    private svc: MateriasService,
    private auth: AuthService,
    private cdr: ChangeDetectorRef,
  ) {
    const now = new Date();
    this.hoyNombre = now.toLocaleDateString('es-CO', { weekday: 'long' });
    this.hoyNombre = this.hoyNombre.charAt(0).toUpperCase() + this.hoyNombre.slice(1);
    this.fechaHoy  = now.toLocaleDateString('es-CO', { day: 'numeric', month: 'long', year: 'numeric' });
  }

  // ── Lifecycle ────────────────────────────────────────────────────────────

  ngOnInit(): void {
    this.username = this.auth.getUser()?.username ?? 'Docente';
    this.sub.add(
      this.svc.materias$.subscribe(list => {
        this.misMaterias = list.filter(m => m.docente === this.username);
        this.bloques     = this.construirBloques();
        this.actualizarStats();
        this.cdr.detectChanges();
      }),
    );
  }

  ngOnDestroy(): void { this.sub.unsubscribe(); }

  // ── Parseo de horario ────────────────────────────────────────────────────

  /** Convierte "Lunes y Miércoles 10:00-12:00" en [{dia,horaInicio,horaFin}] */
  private parsearHorario(horario: string): { dia: number; horaInicio: number; horaFin: number }[] {
    if (!horario?.trim()) return [];

    // Normalizar: minúsculas + quitar tildes
    const h = horario
      .toLowerCase()
      .normalize('NFD')
      .replace(/[̀-ͯ]/g, '');

    // Extraer días mencionados
    const dias: number[] = [];
    for (const [key, val] of Object.entries(DIAS_MAP)) {
      if (h.includes(key) && !dias.includes(val)) dias.push(val);
    }

    // Extraer rango de horas  p.ej.  "10:00-12:00"  "9-11"  "14.30-16"
    const timeRx = /(\d{1,2})[:\.]?(\d{0,2})\s*[-–]\s*(\d{1,2})[:\.]?(\d{0,2})/;
    const m = h.match(timeRx);
    if (!m || dias.length === 0) return [];

    const horaInicio = parseInt(m[1]) + (m[2] ? parseInt(m[2] || '0') / 60 : 0);
    const horaFin    = parseInt(m[3]) + (m[4] ? parseInt(m[4] || '0') / 60 : 0);

    // Ignorar si fuera del rango visible
    if (horaFin <= horaInicio) return [];

    return dias.map(dia => ({ dia, horaInicio, horaFin }));
  }

  // ── Construcción del calendario ──────────────────────────────────────────

  private construirBloques(): CalendarioBloque[] {
    const result: CalendarioBloque[] = [];
    this.misMaterias.forEach((mat, idx) => {
      const { color, light } = PALETA[idx % PALETA.length];
      this.parsearHorario(mat.horario ?? '').forEach(slot => {
        result.push({
          materia:    mat,
          dia:        slot.dia,
          horaInicio: slot.horaInicio,
          horaFin:    slot.horaFin,
          color,
          colorLight: light,
        });
      });
    });
    return result;
  }

  // ── Helpers para el template ─────────────────────────────────────────────

  bloquesEnDia(dia: number): CalendarioBloque[] {
    return this.bloques
      .filter(b => b.dia === dia)
      .sort((a, b) => a.horaInicio - b.horaInicio);
  }

  clasesHoy(): CalendarioBloque[] {
    if (this.diaHoy < 0) return [];
    return this.bloquesEnDia(this.diaHoy);
  }

  getTop(b: CalendarioBloque): number {
    return (b.horaInicio - BASE_HORA) * PX_POR_HORA;
  }

  getHeight(b: CalendarioBloque): number {
    return (b.horaFin - b.horaInicio) * PX_POR_HORA - 4;
  }

  formatHora(h: number): string {
    const hh   = Math.floor(h);
    const mm   = Math.round((h % 1) * 60);
    return `${hh.toString().padStart(2, '0')}:${mm.toString().padStart(2, '0')}`;
  }

  formatRango(b: CalendarioBloque): string {
    return `${this.formatHora(b.horaInicio)} – ${this.formatHora(b.horaFin)}`;
  }

  // ── Stats ────────────────────────────────────────────────────────────────

  private actualizarStats(): void {
    let totalHoras = 0;
    this.misMaterias.forEach(m => {
      this.parsearHorario(m.horario ?? '').forEach(s => {
        totalHoras += s.horaFin - s.horaInicio;
      });
    });
    const hoy = this.clasesHoy().length;
    const pct = Math.min(100, Math.round((this.misMaterias.length / 6) * 100));

    this.stats = [
      { label: 'Materias Asignadas', value: this.misMaterias.length.toString(), subtitle: 'Este semestre', hasDonut: true, donutPercent: pct },
      { label: 'Horas Semanales',    value: totalHoras.toFixed(0),              subtitle: 'Total en horario' },
      { label: 'Clases Hoy',         value: hoy.toString(),                     subtitle: 'Programadas para hoy' },
    ];
  }

  readonly alturaGrid = `${(this.horas.length) * PX_POR_HORA}px`;

  /** Lista de colores para la leyenda del calendario */
  get leyenda(): { nombre: string; color: string }[] {
    return this.misMaterias.map((m, i) => ({
      nombre: m.nombre,
      color:  PALETA[i % PALETA.length].color,
    }));
  }
}
