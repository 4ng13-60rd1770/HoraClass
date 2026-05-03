import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { TeachersGridComponent } from '../../organisms/teachers-grid/teachers-grid.component';
import { Teacher } from '../../molecules/teacher-card/teacher-card.component';
import { TeacherService } from '../../core/services/teacher.service';
import { TeacherRequest, TeacherResponse } from '../../core/models/teacher.models';

@Component({
  selector: 'app-profesores',
  standalone: true,
  imports: [CommonModule, FormsModule, TopbarComponent, TeachersGridComponent],
  templateUrl: './profesores.component.html',
  styleUrls: ['./profesores.component.scss']
})
export class ProfesoresComponent implements OnInit {
  teachers: Teacher[] = [];
  loading = false;
  error = '';

  modalAbierto = false;
  modalEditar = false;
  guardando = false;
  errorModal = '';
  idEditando: number | null = null;

  form: TeacherRequest = this.formVacio();
  formEdit: { carrera: string; cargaHoras: number } = { carrera: '', cargaHoras: 0 };

  constructor(private teacherService: TeacherService, private cdr: ChangeDetectorRef) {}

  ngOnInit() { this.cargarProfesores(); }

  cargarProfesores() {
    this.loading = true;
    this.teacherService.getAll().subscribe({
      next: (data) => {
        this.teachers = data.map(t => this.mapToTeacher(t));
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'No se pudieron cargar los profesores';
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
    if (!this.form.idProfesor || !this.form.primerNombre.trim() || !this.form.primerApellido.trim() || !this.form.username.trim()) {
      this.errorModal = 'Cédula, nombre, apellido y username son obligatorios.';
      return;
    }
    this.guardando = true;
    this.errorModal = '';
    this.teacherService.create(this.form).subscribe({
      next: () => {
        this.guardando = false;
        this.modalAbierto = false;
        this.cdr.detectChanges();
        this.cargarProfesores();
      },
      error: (err) => {
        this.guardando = false;
        this.errorModal = err?.error?.message ?? 'Error al crear el docente.';
        this.cdr.detectChanges();
      }
    });
  }

  // ── Editar ─────────────────────────────────────────────
  abrirEditar(id: number) {
    const t = this.teachers.find(x => x.idProfesor === id);
    if (!t) return;
    this.idEditando = id;
    this.formEdit = { carrera: t.subjects[0] ?? '', cargaHoras: t.loadHours };
    this.errorModal = '';
    this.modalEditar = true;
  }

  cerrarEditar() { this.modalEditar = false; this.errorModal = ''; this.idEditando = null; }

  guardarEdicion() {
    if (!this.idEditando) return;
    this.guardando = true;
    this.teacherService.update(this.idEditando, this.formEdit).subscribe({
      next: () => {
        this.guardando = false;
        this.modalEditar = false;
        this.idEditando = null;
        this.cdr.detectChanges();
        this.cargarProfesores();
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
    if (!confirm('¿Eliminar este docente? También se eliminará su acceso al sistema.')) return;
    this.teacherService.delete(id).subscribe({
      next: () => { this.cdr.detectChanges(); this.cargarProfesores(); },
      error: () => alert('Error al eliminar el docente.')
    });
  }

  private formVacio(): TeacherRequest {
    return { idProfesor: 0, primerNombre: '', primerApellido: '', username: '', carrera: '', cargaHoras: 0 };
  }

  private mapToTeacher(t: TeacherResponse): Teacher {
    return {
      idProfesor: t.idProfesor,
      name: t.nombre,
      title: '',
      role: t.departamento ?? 'Docente',
      subjects: [t.departamento].filter((v): v is string => !!v),
      loadHours: t.cargaHoras ?? 0,
      maxHours: 40
    };
  }
}
