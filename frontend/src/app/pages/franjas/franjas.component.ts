import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ScheduleService } from '../../core/services/schedule.service';
import { TimeSlotRequest, TimeSlotResponse } from '../../core/models/schedule.models';

@Component({
  selector: 'app-franjas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './franjas.component.html',
  styleUrls: ['./franjas.component.scss'],
})
export class FranjasComponent implements OnInit {
  franjas: TimeSlotResponse[] = [];
  loading = false;
  showForm = false;
  editingId: number | null = null;
  errorMsg = '';
  successMsg = '';

  form: TimeSlotRequest = {
    diaSemana: 'LUNES',
    horaInicio: '07:00',
    horaFin: '09:00',
    turno: 'MANANA',
    bloqueado: false,
  };

  dias = ['LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO'];
  turnos = ['MANANA', 'TARDE', 'NOCHE'];

  constructor(private scheduleService: ScheduleService) {}

  ngOnInit(): void {
    this.cargarFranjas();
  }

  cargarFranjas(): void {
    this.loading = true;
    this.scheduleService.getFranjas().subscribe({
      next: (data) => {
        this.franjas = data;
        this.loading = false;
      },
      error: (err) => {
        this.errorMsg = 'Error al cargar franjas horarias';
        this.loading = false;
      },
    });
  }

  abrirFormulario(): void {
    this.showForm = true;
    this.editingId = null;
    this.form = {
      diaSemana: 'LUNES',
      horaInicio: '07:00',
      horaFin: '09:00',
      turno: 'MANANA',
      bloqueado: false,
    };
    this.errorMsg = '';
  }

  editar(franja: TimeSlotResponse): void {
    this.showForm = true;
    this.editingId = franja.idFranja;
    this.form = {
      diaSemana: franja.diaSemana,
      horaInicio: franja.horaInicio,
      horaFin: franja.horaFin,
      turno: franja.turno,
      bloqueado: franja.bloqueado,
    };
    this.errorMsg = '';
  }

  guardar(): void {
    this.loading = true;
    this.errorMsg = '';

    if (this.editingId) {
      this.scheduleService.actualizarFranja(this.editingId, this.form).subscribe({
        next: () => {
          this.successMsg = 'Franja actualizada exitosamente';
          this.showForm = false;
          this.cargarFranjas();
        },
        error: (err) => {
          this.errorMsg = 'Error al actualizar la franja';
          this.loading = false;
        },
      });
    } else {
      this.scheduleService.crearFranja(this.form).subscribe({
        next: () => {
          this.successMsg = 'Franja creada exitosamente';
          this.showForm = false;
          this.cargarFranjas();
        },
        error: (err) => {
          this.errorMsg = 'Error al crear la franja';
          this.loading = false;
        },
      });
    }

    setTimeout(() => (this.successMsg = ''), 3000);
  }

  eliminar(id: number): void {
    if (confirm('¿Está seguro de eliminar esta franja horaria?')) {
      this.scheduleService.eliminarFranja(id).subscribe({
        next: () => {
          this.successMsg = 'Franja eliminada exitosamente';
          this.cargarFranjas();
          setTimeout(() => (this.successMsg = ''), 3000);
        },
        error: () => {
          this.errorMsg = 'Error al eliminar la franja';
        },
      });
    }
  }

  cancelar(): void {
    this.showForm = false;
    this.editingId = null;
    this.errorMsg = '';
  }

  getDiaLabel(dia: string): string {
    const labels: Record<string, string> = {
      LUNES: 'Lunes',
      MARTES: 'Martes',
      MIERCOLES: 'Miércoles',
      JUEVES: 'Jueves',
      VIERNES: 'Viernes',
      SABADO: 'Sábado',
    };
    return labels[dia] || dia;
  }

  getTurnoLabel(turno: string): string {
    const labels: Record<string, string> = {
      MANANA: 'Mañana',
      TARDE: 'Tarde',
      NOCHE: 'Noche',
    };
    return labels[turno] || turno;
  }
}
