import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ScheduleService } from '../../core/services/schedule.service';
import {
  DEFAULT_SCHEDULING_RULES,
  SchedulingRules,
  cupoMaximoPermitido,
  resumenReglas,
} from '../../core/models/scheduling.models';
import { validateSchedulingRulesForm } from '../../core/utils/scheduling-validation.util';

@Component({
  selector: 'app-reglas-academicas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reglas-academicas.component.html',
  styleUrls: ['./reglas-academicas.component.scss'],
})
export class ReglasAcademicasComponent implements OnInit {
  reglas: SchedulingRules = { ...DEFAULT_SCHEDULING_RULES };
  loading = true;
  guardando = false;
  errorMsg = '';
  successMsg = '';

  constructor(private scheduleService: ScheduleService) {}

  ngOnInit(): void {
    this.cargarReglas();
  }

  get cupoMax(): number {
    return cupoMaximoPermitido(this.reglas);
  }

  get resumen(): string {
    return resumenReglas(this.reglas);
  }

  cargarReglas(): void {
    this.loading = true;
    this.errorMsg = '';
    this.scheduleService.getReglas().subscribe({
      next: r => {
        this.reglas = { ...r };
        this.loading = false;
      },
      error: () => {
        this.reglas = { ...DEFAULT_SCHEDULING_RULES };
        this.loading = false;
        this.errorMsg = 'No se pudieron cargar las reglas; se muestran valores por defecto.';
      },
    });
  }

  guardar(): void {
    const err = validateSchedulingRulesForm(this.reglas);
    if (err) {
      this.errorMsg = err;
      this.successMsg = '';
      return;
    }
    this.guardando = true;
    this.errorMsg = '';
    this.successMsg = '';
    this.scheduleService.updateReglas(this.reglas).subscribe({
      next: r => {
        this.reglas = { ...r };
        this.guardando = false;
        this.successMsg = 'Reglas actualizadas correctamente.';
      },
      error: e => {
        this.guardando = false;
        this.errorMsg = e?.error?.message ?? 'Error al guardar las reglas.';
      },
    });
  }

  restablecer(): void {
    if (!confirm('¿Restablecer todas las reglas a los valores institucionales por defecto?')) {
      return;
    }
    this.guardando = true;
    this.errorMsg = '';
    this.successMsg = '';
    this.scheduleService.restablecerReglas().subscribe({
      next: r => {
        this.reglas = { ...r };
        this.guardando = false;
        this.successMsg = 'Reglas restablecidas a los valores por defecto.';
      },
      error: e => {
        this.guardando = false;
        this.errorMsg = e?.error?.message ?? 'Error al restablecer las reglas.';
      },
    });
  }
}
