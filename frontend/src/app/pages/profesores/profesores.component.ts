import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { TeachersGridComponent } from '../../organisms/teachers-grid/teachers-grid.component';
import { Teacher } from '../../molecules/teacher-card/teacher-card.component';
import { TeacherService } from '../../core/services/teacher.service';
import { CourseService } from '../../core/services/course.service';
import { ScheduleService } from '../../core/services/schedule.service';
import { TeacherRequest, TeacherResponse } from '../../core/models/teacher.models';
import { CourseResponse } from '../../core/models/course.models';
import {
  ESCALAFONES,
  RESTRICCIONES_HORARIO,
  SchedulingRules,
  TIPOS_VINCULACION,
  cargaHorasPorVinculacion,
  labelRestriccion,
  labelVinculacion,
} from '../../core/models/scheduling.models';
import { validateTeacherForm } from '../../core/utils/scheduling-validation.util';

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
  formEdit: TeacherRequest = this.formVacio();

  cursos: CourseResponse[] = [];
  reglas: SchedulingRules | null = null;

  readonly tiposVinculacion = TIPOS_VINCULACION;
  readonly restricciones = RESTRICCIONES_HORARIO;
  readonly escalafones = ESCALAFONES;

  constructor(
    private teacherService: TeacherService,
    private courseService: CourseService,
    private scheduleService: ScheduleService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.cargarProfesores();
    this.courseService.getAll().subscribe({ next: c => this.cursos = c });
    this.scheduleService.getReglas().subscribe({ next: r => this.reglas = r });
  }

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

  abrirModal() {
    this.form = this.formVacio();
    this.errorModal = '';
    this.modalAbierto = true;
  }

  cerrarModal() { this.modalAbierto = false; this.errorModal = ''; }

  sugerirUsername() {
    if (this.form.primerNombre && this.form.primerApellido && !this.form.username) {
      this.form.username = (this.form.primerNombre[0] + this.form.primerApellido)
        .toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, '');
    }
  }

  onVinculacionChange() {
    if (this.form.tipoVinculacion && this.reglas) {
      this.form.cargaHoras = cargaHorasPorVinculacion(this.form.tipoVinculacion, this.reglas);
    }
  }

  onVinculacionEditChange() {
    if (this.formEdit.tipoVinculacion && this.reglas) {
      this.formEdit.cargaHoras = cargaHorasPorVinculacion(this.formEdit.tipoVinculacion, this.reglas);
    }
  }

  toggleCurso(form: TeacherRequest, idCurso: number) {
    const list = form.cursosHabilitados ?? [];
    const idx = list.indexOf(idCurso);
    if (idx >= 0) {
      list.splice(idx, 1);
    } else {
      list.push(idCurso);
    }
    form.cursosHabilitados = [...list];
  }

  cursoSeleccionado(form: TeacherRequest, idCurso: number): boolean {
    return (form.cursosHabilitados ?? []).includes(idCurso);
  }

  guardar() {
    if (!this.form.idProfesor || !this.form.primerNombre.trim() || !this.form.primerApellido.trim() || !this.form.username.trim()) {
      this.errorModal = 'Cédula, nombre, apellido y username son obligatorios.';
      return;
    }
    if (this.reglas) {
      const err = validateTeacherForm(this.form, this.reglas, this.cursos.length);
      if (err) {
        this.errorModal = err;
        return;
      }
      if (!this.form.cargaHoras) {
        this.form.cargaHoras = cargaHorasPorVinculacion(this.form.tipoVinculacion!, this.reglas);
      }
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

  abrirEditar(id: number) {
    this.idEditando = id;
    this.errorModal = '';
    this.teacherService.getById(id).subscribe({
      next: (t) => {
        this.formEdit = {
          idProfesor: t.idProfesor,
          primerNombre: t.nombre.split(' ')[0] ?? '',
          primerApellido: t.nombre.split(' ').slice(1).join(' ') ?? '',
          username: t.username,
          carrera: t.departamento ?? '',
          cargaHoras: t.cargaHoras ?? 0,
          escalafon: t.escalafon ?? 'ASISTENTE',
          tipoVinculacion: t.tipoVinculacion ?? 'TIEMPO_COMPLETO',
          restriccionHorario: t.restriccionHorario ?? 'SIN_RESTRICCION',
          cursosHabilitados: [...(t.cursosHabilitados ?? [])],
        };
        this.modalEditar = true;
        this.cdr.detectChanges();
      },
      error: () => {
        this.errorModal = 'No se pudo cargar el docente.';
      }
    });
  }

  cerrarEditar() { this.modalEditar = false; this.errorModal = ''; this.idEditando = null; }

  guardarEdicion() {
    if (!this.idEditando) return;
    if (this.reglas) {
      const err = validateTeacherForm(this.formEdit, this.reglas, this.cursos.length);
      if (err) {
        this.errorModal = err;
        return;
      }
    }
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

  eliminar(id: number) {
    if (!confirm('¿Eliminar este docente? También se eliminará su acceso al sistema.')) return;
    this.teacherService.delete(id).subscribe({
      next: () => { this.cdr.detectChanges(); this.cargarProfesores(); },
      error: () => alert('Error al eliminar el docente.')
    });
  }

  private formVacio(): TeacherRequest {
    return {
      idProfesor: 0,
      primerNombre: '',
      primerApellido: '',
      username: '',
      carrera: '',
      cargaHoras: 0,
      escalafon: 'ASISTENTE',
      tipoVinculacion: 'TIEMPO_COMPLETO',
      restriccionHorario: 'SIN_RESTRICCION',
      cursosHabilitados: [],
    };
  }

  private mapToTeacher(t: TeacherResponse): Teacher {
    const extras: string[] = [];
    if (t.tipoVinculacion) extras.push(labelVinculacion(t.tipoVinculacion));
    if (t.restriccionHorario && t.restriccionHorario !== 'SIN_RESTRICCION') {
      extras.push(labelRestriccion(t.restriccionHorario));
    }
    if (t.escalafon) extras.push(t.escalafon);
    return {
      idProfesor: t.idProfesor,
      name: t.nombre,
      title: t.escalafon ?? '',
      role: t.departamento ?? 'Docente',
      subjects: extras.length ? extras : [t.departamento].filter((v): v is string => !!v),
      loadHours: t.cargaHoras ?? 0,
      maxHours: t.cargaHoras ?? 20,
    };
  }
}
