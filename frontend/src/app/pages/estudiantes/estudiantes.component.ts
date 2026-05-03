import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { StudentsTableComponent, StudentRow } from '../../organisms/students-table/students-table.component';
import { BadgeVariant } from '../../atoms/badge/badge.component';
import { StudentService } from '../../core/services/student.service';
import { StudentRequest } from '../../core/models/student.models';

@Component({
  selector: 'app-estudiantes',
  standalone: true,
  imports: [CommonModule, FormsModule, TopbarComponent, StudentsTableComponent],
  templateUrl: './estudiantes.component.html',
  styleUrls: ['./estudiantes.component.scss']
})
export class EstudiantesComponent implements OnInit {
  searchQuery = '';
  students: StudentRow[] = [];
  loading = false;
  error = '';

  modalAbierto = false;
  modalEditar = false;
  guardando = false;
  errorModal = '';
  idEditando: number | null = null;

  form: StudentRequest = this.formVacio();
  formEdit: { carrera: string; semestre: number; creditos: number } = { carrera: '', semestre: 1, creditos: 0 };

  constructor(private studentService: StudentService, private cdr: ChangeDetectorRef) {}

  ngOnInit() { this.cargarEstudiantes(); }

  cargarEstudiantes() {
    this.loading = true;
    this.studentService.getAll().subscribe({
      next: (data) => {
        this.students = data.map(s => ({
          idNum: s.idEstudiante,
          name: s.nombre,
          email: s.username,
          id: `#EST-${s.idEstudiante}`,
          career: s.carrera ?? 'N/A',
          enrolledDate: `Semestre ${s.semestre ?? 1}`,
          status: s.estado === 'ACTIVO' ? 'Completo' : 'Pendiente',
          statusVariant: (s.estado === 'ACTIVO' ? 'completo' : 'pendiente') as BadgeVariant
        }));
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'No se pudieron cargar los estudiantes';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  // ── Crear ──────────────────────────────────────────────
  abrirModal() {
    this.form = this.formVacio();
    this.errorModal = '';
    this.modalAbierto = true;
  }

  cerrarModal() { this.modalAbierto = false; this.errorModal = ''; }

  sugerirUsername() {
    if (this.form.primerNombre && this.form.primerApellido && !this.form.username) {
      this.form.username = (this.form.primerNombre[0] + this.form.primerApellido)
        .toLowerCase().normalize('NFD').replace(/[̀-ͯ]/g, '');
    }
  }

  guardar() {
    if (!this.form.idEstudiante || !this.form.primerNombre.trim() || !this.form.primerApellido.trim() || !this.form.username.trim()) {
      this.errorModal = 'Cédula, nombre, apellido y username son obligatorios.';
      return;
    }
    this.guardando = true;
    this.errorModal = '';
    this.studentService.create(this.form).subscribe({
      next: () => {
        this.guardando = false;
        this.modalAbierto = false;
        this.cdr.detectChanges();
        this.cargarEstudiantes();
      },
      error: (err) => {
        this.guardando = false;
        this.errorModal = err?.error?.message ?? 'Error al crear el estudiante.';
        this.cdr.detectChanges();
      }
    });
  }

  // ── Editar ─────────────────────────────────────────────
  abrirEditar(id: number) {
    const s = this.students.find(x => x.idNum === id);
    if (!s) return;
    this.idEditando = id;
    const sem = parseInt(s.enrolledDate.replace('Semestre ', '')) || 1;
    this.formEdit = { carrera: s.career, semestre: sem, creditos: 0 };
    this.errorModal = '';
    this.modalEditar = true;
  }

  cerrarEditar() { this.modalEditar = false; this.errorModal = ''; this.idEditando = null; }

  guardarEdicion() {
    if (!this.idEditando) return;
    this.guardando = true;
    this.studentService.update(this.idEditando, this.formEdit as any).subscribe({
      next: () => {
        this.guardando = false;
        this.modalEditar = false;
        this.idEditando = null;
        this.cdr.detectChanges();
        this.cargarEstudiantes();
      },
      error: (err) => {
        this.guardando = false;
        this.errorModal = err?.error?.message ?? 'Error al actualizar.';
        this.cdr.detectChanges();
      }
    });
  }

  // ── Eliminar ───────────────────────────────────────────
  eliminar(id: number) {
    if (!confirm('¿Eliminar este estudiante? También se eliminará su acceso al sistema.')) return;
    this.studentService.delete(id).subscribe({
      next: () => { this.cdr.detectChanges(); this.cargarEstudiantes(); },
      error: () => alert('Error al eliminar el estudiante.')
    });
  }

  private formVacio(): StudentRequest {
    return { idEstudiante: 0, primerNombre: '', primerApellido: '', username: '', carrera: '', semestre: 1, creditos: 0 };
  }
}
