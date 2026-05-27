import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { forkJoin } from 'rxjs';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { StatsRowComponent, StatData } from '../../organisms/stats-row/stats-row.component';
import { StudentService } from '../../core/services/student.service';
import { TeacherService } from '../../core/services/teacher.service';
import { ClassroomService } from '../../core/services/classroom.service';
import { ClassroomResponse } from '../../core/models/classroom.models';
import { MateriasService, MateriaShared } from '../../core/services/materias.service';

// ── SVG icons para stats ────────────────────────────────────────────────────
const SVG_CLASSROOM = `<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="#5a8a5e" stroke-width="1.6">
  <rect x="2" y="7" width="20" height="14" rx="2"/><path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"/>
</svg>`;
const SVG_BRIEFCASE = `<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="#5a8a5e" stroke-width="1.6">
  <rect x="2" y="7" width="20" height="14" rx="2"/><path d="M16 7V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v2"/>
  <line x1="2" y1="12" x2="22" y2="12"/>
</svg>`;

// ── Tipos del horario picker ────────────────────────────────────────────────
interface HorarioPicker {
  dias:   boolean[];   // [Lun, Mar, Mié, Jue, Vie]
  inicio: string;      // "08:00"
  fin:    string;      // "10:00"
}

// ── Constantes ──────────────────────────────────────────────────────────────
const DIAS_NOMBRES = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes'];

const DIAS_MAP: Record<string, number> = {
  lunes: 0, lun: 0,
  martes: 1, mar: 1,
  miercoles: 2, mie: 2,
  jueves: 3, jue: 3,
  viernes: 4, vie: 4,
};

@Component({
  selector: 'app-dashboard-secretaria',
  standalone: true,
  imports: [CommonModule, FormsModule, TopbarComponent, StatsRowComponent],
  templateUrl: './dashboard-secretaria.component.html',
  styleUrls: ['./dashboard-secretaria.component.scss'],
})
export class DashboardSecretariaComponent implements OnInit, OnDestroy {

  // ── Stats ─────────────────────────────────────────────────────────────────
  stats: StatData[] = [
    { label: 'Estudiantes Registrados', value: '—', subtitle: 'En el sistema', hasDonut: true, donutPercent: 0 },
    { label: 'Disponibilidad de Aula',  value: '—', subtitle: 'Disponible',    svgIcon: SVG_CLASSROOM },
    { label: 'Docentes Registrados',    value: '—', subtitle: 'En el sistema', svgIcon: SVG_BRIEFCASE },
  ];

  // ── Datos ─────────────────────────────────────────────────────────────────
  allClassrooms: ClassroomResponse[] = [];
  materias:      MateriaShared[]     = [];
  filtroCarrera  = '';
  errorModal     = '';

  // ── Modales ───────────────────────────────────────────────────────────────
  modalAbrir  = false;
  modalEditar = false;
  idEditando: number | null = null;

  // ── Formularios ───────────────────────────────────────────────────────────
  form     = this.formVacio();
  formEdit = this.formVacio();

  // ── Horario pickers ───────────────────────────────────────────────────────
  readonly diasNombres = DIAS_NOMBRES;
  formHorario:     HorarioPicker = this.horarioVacio();
  formEditHorario: HorarioPicker = this.horarioVacio();

  private sub = new Subscription();

  // ── Constructor ───────────────────────────────────────────────────────────
  constructor(
    private studentService:   StudentService,
    private teacherService:   TeacherService,
    private classroomService: ClassroomService,
    private svc:              MateriasService,
    private cdr:              ChangeDetectorRef,
  ) {}

  // ── Lifecycle ─────────────────────────────────────────────────────────────
  ngOnInit(): void {
    this.sub.add(
      this.svc.materias$.subscribe(list => {
        this.materias = list;
        this.cdr.detectChanges();
      }),
    );

    forkJoin({
      students:   this.studentService.getAll(),
      teachers:   this.teacherService.getAll(),
      classrooms: this.classroomService.getAll(),
    }).subscribe({
      next: ({ students, teachers, classrooms }) => {
        this.allClassrooms = classrooms;
        const total = classrooms.length;
        const disp  = classrooms.filter(s => s.disponible).length;
        this.stats = [
          { label: 'Estudiantes Registrados', value: students.length.toString(),  subtitle: 'En el sistema', hasDonut: true, donutPercent: 0 },
          { label: 'Disponibilidad de Aula',  value: `${disp}/${total}`,           subtitle: 'Disponible',    svgIcon: SVG_CLASSROOM },
          { label: 'Docentes Registrados',    value: teachers.length.toString(),   subtitle: 'En el sistema', svgIcon: SVG_BRIEFCASE },
        ];
        this.cdr.detectChanges();
      },
      error: () => {},
    });
  }

  ngOnDestroy(): void { this.sub.unsubscribe(); }

  // ── Getters de vista ──────────────────────────────────────────────────────
  get carreras(): string[] {
    return [...new Set(this.materias.map(m => m.carrera).filter(Boolean))];
  }

  get materiasFiltradas(): MateriaShared[] {
    return this.filtroCarrera
      ? this.materias.filter(m => m.carrera === this.filtroCarrera)
      : this.materias;
  }

  salonesParaCarrera(carrera: string): ClassroomResponse[] {
    return this.allClassrooms.filter(
      s => s.disponible && (!s.departamento || s.departamento === '' || s.departamento === carrera),
    );
  }

  progreso(m: MateriaShared): number {
    return m.cupoMax ? Math.min(100, Math.round((m.inscritos / m.cupoMax) * 100)) : 0;
  }

  // ── Validación de salones ─────────────────────────────────────────────────
  get sinSalonesCrear(): boolean {
    return !!this.form.carrera && this.salonesParaCarrera(this.form.carrera).length === 0;
  }

  get sinSalonesEditar(): boolean {
    return !!this.formEdit.carrera && this.salonesParaCarrera(this.formEdit.carrera).length === 0;
  }

  // ── Horario picker ────────────────────────────────────────────────────────
  private horarioVacio(): HorarioPicker {
    return { dias: [false, false, false, false, false], inicio: '08:00', fin: '10:00' };
  }

  buildHorario(h: HorarioPicker): string {
    const sel = DIAS_NOMBRES.filter((_, i) => h.dias[i]);
    if (sel.length === 0 || !h.inicio || !h.fin) return '';
    return `${sel.join(' y ')} ${h.inicio}–${h.fin}`;
  }

  /** Convierte string "Lunes y Miércoles 10:00–12:00" de vuelta al picker */
  private pickerFromString(horario: string): HorarioPicker {
    const p = this.horarioVacio();
    if (!horario) return p;
    const h = horario.toLowerCase().normalize('NFD').replace(/[̀-ͯ]/g, '');
    for (const [key, idx] of Object.entries(DIAS_MAP)) {
      if (h.includes(key)) p.dias[idx] = true;
    }
    const m = horario.match(/(\d{1,2}:\d{2})\s*[-–]\s*(\d{1,2}:\d{2})/);
    if (m) { p.inicio = m[1]; p.fin = m[2]; }
    return p;
  }

  // ── CRUD Materias ─────────────────────────────────────────────────────────
  abrirModal(): void {
    this.form        = this.formVacio();
    this.formHorario = this.horarioVacio();
    this.errorModal  = '';
    this.modalAbrir  = true;
  }

  cerrarModal(): void { this.modalAbrir = false; this.errorModal = ''; }

  guardarMateria(): void {
    if (!this.form.nombre.trim() || !this.form.carrera) {
      this.errorModal = 'Nombre y carrera son obligatorios.';
      return;
    }
    if (this.sinSalonesCrear) {
      this.errorModal = 'No hay salones disponibles para esta carrera. Primero registra un salón en la sección Salones.';
      return;
    }
    const horario = this.buildHorario(this.formHorario);
    const salon   = this.allClassrooms.find(s => s.idSalon === this.form.salonId);
    this.svc.add({
      nombre:      this.form.nombre,
      carrera:     this.form.carrera,
      cupoMax:     this.form.cupoMax || 30,
      horario,
      salonId:     this.form.salonId,
      salonNombre: salon?.nombre,
      codigo:      this.form.codigo,
      tipo:        this.form.tipo,
      modalidad:   this.form.modalidad,
    });
    this.modalAbrir  = false;
    this.form        = this.formVacio();
    this.formHorario = this.horarioVacio();
  }

  abrirEditar(m: MateriaShared): void {
    this.idEditando = m.id;
    this.formEdit = {
      nombre:   m.nombre,    carrera:   m.carrera,   cupoMax:  m.cupoMax,
      horario:  m.horario,   salonId:   m.salonId ?? null,
      codigo:   m.codigo ?? '', tipo: m.tipo ?? 'Obligatoria', modalidad: m.modalidad ?? 'Diurna',
    };
    this.formEditHorario = this.pickerFromString(m.horario ?? '');
    this.errorModal  = '';
    this.modalEditar = true;
  }

  cerrarEditar(): void { this.modalEditar = false; this.errorModal = ''; this.idEditando = null; }

  guardarEdicion(): void {
    if (!this.formEdit.nombre.trim() || !this.formEdit.carrera) {
      this.errorModal = 'Nombre y carrera son obligatorios.';
      return;
    }
    if (this.sinSalonesEditar) {
      this.errorModal = 'No hay salones disponibles para esta carrera.';
      return;
    }
    const horario = this.buildHorario(this.formEditHorario);
    const salon   = this.allClassrooms.find(s => s.idSalon === this.formEdit.salonId);
    this.svc.update(this.idEditando!, {
      nombre:      this.formEdit.nombre,   carrera:     this.formEdit.carrera,
      cupoMax:     this.formEdit.cupoMax,  horario,
      salonId:     this.formEdit.salonId,  salonNombre: salon?.nombre,
      codigo:      this.formEdit.codigo,   tipo:        this.formEdit.tipo,
      modalidad:   this.formEdit.modalidad,
    });
    this.modalEditar = false;
    this.idEditando  = null;
  }

  eliminar(id: number): void {
    if (!confirm('¿Eliminar esta materia?')) return;
    this.svc.remove(id);
  }

  // ── Form vacío ────────────────────────────────────────────────────────────
  private formVacio() {
    return {
      nombre: '', carrera: '', cupoMax: 30, horario: '',
      salonId: null as number | null,
      codigo: '', tipo: 'Obligatoria', modalidad: 'Diurna',
    };
  }
}
