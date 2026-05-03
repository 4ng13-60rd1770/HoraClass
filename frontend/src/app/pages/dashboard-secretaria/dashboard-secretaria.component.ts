import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { StatsRowComponent, StatData } from '../../organisms/stats-row/stats-row.component';
import { StudentService } from '../../core/services/student.service';
import { TeacherService } from '../../core/services/teacher.service';
import { ClassroomService } from '../../core/services/classroom.service';
import { ClassroomResponse } from '../../core/models/classroom.models';
import { MateriasService, MateriaShared } from '../../core/services/materias.service';
import { forkJoin } from 'rxjs';

const SVG_CLASSROOM = `<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="#5a8a5e" stroke-width="1.6">
  <rect x="2" y="7" width="20" height="14" rx="2"/><path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"/>
</svg>`;
const SVG_BRIEFCASE = `<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="#5a8a5e" stroke-width="1.6">
  <rect x="2" y="7" width="20" height="14" rx="2"/><path d="M16 7V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v2"/>
  <line x1="2" y1="12" x2="22" y2="12"/>
</svg>`;

@Component({
  selector: 'app-dashboard-secretaria',
  standalone: true,
  imports: [CommonModule, FormsModule, TopbarComponent, StatsRowComponent],
  templateUrl: './dashboard-secretaria.component.html',
  styleUrls: ['./dashboard-secretaria.component.scss']
})
export class DashboardSecretariaComponent implements OnInit, OnDestroy {

  stats: StatData[] = [
    { label: 'Estudiantes Registrados', value: '—', subtitle: 'En el sistema', hasDonut: true, donutPercent: 0 },
    { label: 'Disponibilidad de Aula',  value: '—', subtitle: 'Disponible',    svgIcon: SVG_CLASSROOM },
    { label: 'Docentes Registrados',    value: '—', subtitle: 'En el sistema', svgIcon: SVG_BRIEFCASE }
  ];

  allClassrooms: ClassroomResponse[] = [];
  filtroCarrera = '';
  errorModal = '';

  /** Lista reactiva — se actualiza automáticamente desde el servicio */
  materias: MateriaShared[] = [];

  modalAbrir  = false;
  modalEditar = false;
  idEditando: number | null = null;
  form     = this.formVacio();
  formEdit = this.formVacio();

  private sub = new Subscription();

  get carreras(): string[] {
    return [...new Set(this.materias.map(m => m.carrera).filter(Boolean))];
  }

  get materiasFiltradas(): MateriaShared[] {
    return this.filtroCarrera
      ? this.materias.filter(m => m.carrera === this.filtroCarrera)
      : this.materias;
  }

  salonesParaCarrera(carrera: string): ClassroomResponse[] {
    return this.allClassrooms.filter(s =>
      s.disponible && (!s.departamento || s.departamento === '' || s.departamento === carrera)
    );
  }

  progreso(m: MateriaShared): number {
    return m.cupoMax ? Math.min(100, Math.round((m.inscritos / m.cupoMax) * 100)) : 0;
  }

  constructor(
    private studentService: StudentService,
    private teacherService: TeacherService,
    private classroomService: ClassroomService,
    private svc: MateriasService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    // Suscripción reactiva: cada vez que el servicio cambia, el dashboard se actualiza
    this.sub.add(
      this.svc.materias$.subscribe(list => {
        this.materias = list;
        this.cdr.detectChanges();
      })
    );

    forkJoin({
      students:   this.studentService.getAll(),
      teachers:   this.teacherService.getAll(),
      classrooms: this.classroomService.getAll()
    }).subscribe({
      next: ({ students, teachers, classrooms }) => {
        this.allClassrooms = classrooms;
        const total = classrooms.length;
        const disp  = classrooms.filter(s => s.disponible).length;
        this.stats = [
          { label: 'Estudiantes Registrados', value: students.length.toString(), subtitle: 'En el sistema', hasDonut: true, donutPercent: 0 },
          { label: 'Disponibilidad de Aula',  value: `${disp}/${total}`,          subtitle: 'Disponible',    svgIcon: SVG_CLASSROOM },
          { label: 'Docentes Registrados',    value: teachers.length.toString(),  subtitle: 'En el sistema', svgIcon: SVG_BRIEFCASE }
        ];
        this.cdr.detectChanges();
      },
      error: () => {}
    });
  }

  ngOnDestroy() { this.sub.unsubscribe(); }

  // ── Crear ──────────────────────────────────────────────
  abrirModal() {
    this.form = this.formVacio();
    this.errorModal = '';
    this.modalAbrir = true;
  }

  cerrarModal() { this.modalAbrir = false; this.errorModal = ''; }

  guardarMateria() {
    if (!this.form.nombre.trim() || !this.form.carrera) {
      this.errorModal = 'Nombre y carrera son obligatorios.';
      return;
    }
    const salon = this.allClassrooms.find(s => s.idSalon === this.form.salonId);
    this.svc.add({
      nombre:      this.form.nombre,
      carrera:     this.form.carrera,
      cupoMax:     this.form.cupoMax || 30,
      horario:     this.form.horario,
      salonId:     this.form.salonId,
      salonNombre: salon?.nombre,
      codigo:      this.form.codigo,
      tipo:        this.form.tipo,
      modalidad:   this.form.modalidad
    });
    this.modalAbrir = false;
    this.form = this.formVacio();
  }

  // ── Editar ─────────────────────────────────────────────
  abrirEditar(m: MateriaShared) {
    this.idEditando = m.id;
    this.formEdit = {
      nombre:    m.nombre,   carrera:   m.carrera,  cupoMax:   m.cupoMax,
      horario:   m.horario,  salonId:   m.salonId ?? null,
      codigo:    m.codigo ?? '', tipo: m.tipo ?? 'Obligatoria', modalidad: m.modalidad ?? 'Diurna'
    };
    this.errorModal = '';
    this.modalEditar = true;
  }

  cerrarEditar() { this.modalEditar = false; this.errorModal = ''; this.idEditando = null; }

  guardarEdicion() {
    if (!this.formEdit.nombre.trim() || !this.formEdit.carrera) {
      this.errorModal = 'Nombre y carrera son obligatorios.';
      return;
    }
    const salon = this.allClassrooms.find(s => s.idSalon === this.formEdit.salonId);
    this.svc.update(this.idEditando!, {
      nombre:      this.formEdit.nombre,   carrera:     this.formEdit.carrera,
      cupoMax:     this.formEdit.cupoMax,  horario:     this.formEdit.horario,
      salonId:     this.formEdit.salonId,  salonNombre: salon?.nombre,
      codigo:      this.formEdit.codigo,   tipo:        this.formEdit.tipo,
      modalidad:   this.formEdit.modalidad
    });
    this.modalEditar = false;
    this.idEditando = null;
  }

  // ── Eliminar ───────────────────────────────────────────
  eliminar(id: number) {
    if (!confirm('¿Eliminar esta materia?')) return;
    this.svc.remove(id);
  }

  private formVacio() {
    return { nombre: '', carrera: '', cupoMax: 30,
             horario: '', salonId: null as number | null,
             codigo: '', tipo: 'Obligatoria', modalidad: 'Diurna' };
  }
}
