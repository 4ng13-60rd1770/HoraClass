import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { StatsRowComponent, StatData } from '../../organisms/stats-row/stats-row.component';
import { TodayScheduleComponent, ScheduleEntry } from '../../organisms/today-schedule/today-schedule.component';

@Component({
  selector: 'app-dashboard-estudiante',
  standalone: true,
  imports: [CommonModule, TopbarComponent, StatsRowComponent, TodayScheduleComponent],
  templateUrl: './dashboard-estudiante.component.html',
  styleUrls: ['./dashboard-estudiante.component.scss']
})
export class DashboardEstudianteComponent {
  stats: StatData[] = [
    { label: 'Total de Créditos', value: '17', valueClass: 'green', subtitle: 'Créditos Sugeridos: 12', icon: 'assets/icons/credits.png' },
    { label: 'Nota Semestral', value: '4,8', valueExtra: '/5,0', subtitle: 'Nota Mínima: 3,3', icon: 'assets/icons/grades.png' },
    { label: 'Nuevo Examen', value: '4 Días', valueClass: 'blue', subtitle: 'Base de Datos I', icon: 'assets/icons/exam.png' }
  ];

  schedule: ScheduleEntry[] = [
    {
      time: '09:00',
      block: {
        code: 'CS101',
        name: 'CS101 - Introducción a Proyecto de Grado',
        location: 'I-302',
        professor: 'Prof. Helio Gonzales',
        status: 'en-curso',
        color: '#fdf8ec',
        borderColor: '#c49a3c'
      }
    },
    {
      time: '11:30',
      block: {
        code: 'MAT202',
        name: 'MAT202 - Matemáticas II',
        location: 'F-404',
        professor: 'Prof. Lizeth Bedoya',
        status: 'sin-comenzar',
        color: '#f0f4fb',
        borderColor: '#4a7fcb'
      }
    }
  ];
}
