import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ScheduleService } from '../../core/services/schedule.service';
import {
  ScheduleResponse,
  ScheduleEntryResponse,
  ScheduleConflictResponse,
} from '../../core/models/schedule.models';

@Component({
  selector: 'app-generar-horario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './generar-horario.component.html',
  styleUrls: ['./generar-horario.component.scss'],
})
export class GenerarHorarioComponent implements OnInit {
  semestre = '';
  variantes = 1;
  loading = false;
  generando = false;
  errorMsg = '';
  successMsg = '';

  horario: ScheduleResponse | null = null;
  horariosExistentes: ScheduleResponse[] = [];

  // Vista actual: 'tabla' o 'calendario'
  vista: 'tabla' | 'calendario' = 'tabla';

  // Para la vista de calendario
  dias = ['LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO'];
  horas = [
    '07:00', '08:00', '09:00', '10:00', '11:00', '12:00',
    '13:00', '14:00', '15:00', '16:00', '17:00', '18:00',
    '19:00', '20:00'
  ];

  constructor(private scheduleService: ScheduleService) {}

  ngOnInit(): void {
    this.cargarHorariosExistentes();
  }

  cargarHorariosExistentes(): void {
    this.scheduleService.listarHorarios().subscribe({
      next: (data) => (this.horariosExistentes = data),
      error: () => {},
    });
  }

  generarHorario(): void {
    if (!this.semestre.trim()) {
      this.errorMsg = 'Ingrese el semestre (ej: 2026-1)';
      return;
    }

    this.generando = true;
    this.errorMsg = '';
    this.successMsg = '';

    const variantes = Math.max(1, this.variantes);
    this.scheduleService.generarHorario(this.semestre, variantes).subscribe({
      next: (data) => {
        this.horario = data;
        this.generando = false;
        this.successMsg = `Horario generado exitosamente: ${data.totalEntries} asignaciones, ${data.totalConflictos} conflictos`;
        this.cargarHorariosExistentes();
      },
      error: (err) => {
        this.generando = false;
        const msg =
          err.error?.message ||
          err.error?.error ||
          (typeof err.error === 'string' ? err.error : null) ||
          err.message;
        this.errorMsg = msg && msg !== 'Http failure response'
          ? msg
          : 'Error al generar el horario. Verifique que existan materias, docentes y aulas disponibles.';
      },
    });
  }

  consultarHorario(semestre: string): void {
    this.loading = true;
    this.errorMsg = '';
    this.scheduleService.obtenerHorario(semestre).subscribe({
      next: (data) => {
        this.horario = data;
        this.semestre = semestre;
        this.loading = false;
      },
      error: () => {
        this.errorMsg = 'No se encontró horario para el semestre ' + semestre;
        this.loading = false;
      },
    });
  }

  publicar(): void {
    if (!this.horario) return;
    this.loading = true;
    this.scheduleService.publicarHorario(this.horario.semestre).subscribe({
      next: (data) => {
        this.horario = data;
        this.successMsg = 'Horario publicado exitosamente';
        this.loading = false;
        this.cargarHorariosExistentes();
        setTimeout(() => (this.successMsg = ''), 3000);
      },
      error: (err) => {
        this.errorMsg = err.error?.error || 'No se pudo publicar el horario';
        this.loading = false;
      },
    });
  }

  eliminarHorario(): void {
    if (!this.horario) return;
    if (!confirm('¿Está seguro de eliminar este horario? Esta acción no se puede deshacer.')) return;

    this.scheduleService.eliminarHorario(this.horario.semestre).subscribe({
      next: () => {
        this.horario = null;
        this.successMsg = 'Horario eliminado exitosamente';
        this.cargarHorariosExistentes();
        setTimeout(() => (this.successMsg = ''), 3000);
      },
      error: () => {
        this.errorMsg = 'Error al eliminar el horario';
      },
    });
  }

  getEntriesForCell(dia: string, hora: string): ScheduleEntryResponse[] {
    if (!this.horario?.entries) return [];
    return this.horario.entries.filter(
      (e) => e.diaSemana === dia && e.horaInicio === hora
    );
  }

  getDiaLabel(dia: string): string {
    const labels: Record<string, string> = {
      LUNES: 'Lunes', MARTES: 'Martes', MIERCOLES: 'Miércoles',
      JUEVES: 'Jueves', VIERNES: 'Viernes', SABADO: 'Sábado',
    };
    return labels[dia] || dia;
  }

  getMotivoLabel(motivo: string): string {
    const labels: Record<string, string> = {
      SIN_DOCENTE_DISPONIBLE: 'Sin docente disponible',
      SIN_AULA_DISPONIBLE: 'Sin aula disponible',
      SIN_FRANJA_DISPONIBLE: 'Sin bloque horario disponible',
      CARGA_MAXIMA_ALCANZADA: 'Carga máxima alcanzada',
      SIN_AULA_CON_CAPACIDAD: 'Sin aula con capacidad suficiente',
      DOCENTE_DUPLICADO: 'Docente asignado en más de una clase al mismo horario',
      AULA_DUPLICADA: 'Aula asignada a más de un grupo en el mismo horario',
      CRUCE_HORARIO: 'Cruce de horario detectado',
      DOCENTE_NO_HABILITADO_CURSO: 'Docente no habilitado para el curso',
      DOCENTE_RESTRICCION_HORARIO: 'Restricción horaria del docente',
      AULA_SIN_REQUISITOS: 'Aula sin requisitos del curso (PC o sillas móviles)',
    };
    return labels[motivo] || motivo;
  }

  getEntryColor(index: number): string {
    const colors = [
      '#dcfce7', '#dbeafe', '#fef3c7', '#ede9fe', '#fce7f3',
      '#ccfbf1', '#fef9c3', '#e0e7ff', '#fbcfe8', '#d1fae5'
    ];
    return colors[index % colors.length];
  }
}
