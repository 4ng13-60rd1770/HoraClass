import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { StatsRowComponent, StatData } from '../../organisms/stats-row/stats-row.component';
import { TodayScheduleComponent, ScheduleEntry } from '../../organisms/today-schedule/today-schedule.component';
import { StudentService } from '../../core/services/student.service';
import { StudentResponse } from '../../core/models/student.models';

@Component({
  selector: 'app-dashboard-estudiante',
  standalone: true,
  imports: [CommonModule, TopbarComponent, StatsRowComponent, TodayScheduleComponent],
  templateUrl: './dashboard-estudiante.component.html',
  styleUrls: ['./dashboard-estudiante.component.scss']
})
export class DashboardEstudianteComponent implements OnInit {

  perfil: StudentResponse | null = null;

  stats: StatData[] = [
    { label: 'Total de Créditos',  value: '—', subtitle: 'Créditos cargados',  hasDonut: false, donutPercent: 0 },
    { label: 'Nota Semestral',     value: '—', subtitle: 'Nota mínima: 3,3',   hasDonut: false, donutPercent: 0 },
    { label: 'Semestre Actual',    value: '—', subtitle: 'En el sistema',       hasDonut: false, donutPercent: 0 },
  ];

  schedule: ScheduleEntry[] = [];

  constructor(
    private studentSvc: StudentService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.studentSvc.getProfile().subscribe({
      next: (p) => {
        this.perfil = p;
        this.stats = [
          { label: 'Total de Créditos', value: p.creditos?.toString() ?? '—', subtitle: 'Créditos cargados',  hasDonut: false, donutPercent: 0 },
          { label: 'Nota Semestral',    value: '—',                            subtitle: 'Nota mínima: 3,3',   hasDonut: false, donutPercent: 0 },
          { label: 'Semestre Actual',   value: p.semestre?.toString() ?? '—', subtitle: p.carrera ?? '',        hasDonut: false, donutPercent: 0 },
        ];
        this.cdr.detectChanges();
      },
      error: () => { /* si falla, queda en estado vacío */ }
    });
  }

  get nombreMostrar(): string {
    if (!this.perfil) return '...';
    // Muestra solo el primer nombre
    return this.perfil.nombre.split(' ')[0];
  }
}
