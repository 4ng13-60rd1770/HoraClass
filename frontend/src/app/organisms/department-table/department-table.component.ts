import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BadgeComponent } from '../../atoms/badge/badge.component';
import { ProgressBarComponent } from '../../atoms/progress-bar/progress-bar.component';
import { BadgeVariant } from '../../atoms/badge/badge.component';

export interface DepartmentRow {
  department: string;
  coordinator: string;
  courses: number;
  status: string;
  statusVariant: BadgeVariant;
  progress: number;
  progressColor: string;
}

@Component({
  selector: 'app-department-table',
  standalone: true,
  imports: [CommonModule, BadgeComponent, ProgressBarComponent],
  template: `
    <div class="dept-table-container">
      <h2 class="dept-table__title">Estado de Departamento</h2>
      <table class="dept-table">
        <thead>
          <tr>
            <th>Departamento</th>
            <th>Coordinador</th>
            <th>Cursos</th>
            <th>Estado</th>
            <th>Progreso</th>
            <th>Acción</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let row of rows" class="dept-table__row">
            <td class="dept-table__dept">{{ row.department }}</td>
            <td class="dept-table__coord">{{ row.coordinator }}</td>
            <td class="dept-table__courses">{{ row.courses }} Cursos</td>
            <td><app-badge [variant]="row.statusVariant">{{ row.status }}</app-badge></td>
            <td class="dept-table__progress">
              <app-progress-bar [percent]="row.progress" [color]="row.progressColor"></app-progress-bar>
            </td>
            <td>
              <button class="action-btn">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <line x1="8" y1="6" x2="21" y2="6"/><line x1="8" y1="12" x2="21" y2="12"/>
                  <line x1="8" y1="18" x2="21" y2="18"/><line x1="3" y1="6" x2="3.01" y2="6"/>
                  <line x1="3" y1="12" x2="3.01" y2="12"/><line x1="3" y1="18" x2="3.01" y2="18"/>
                </svg>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `,
  styleUrls: ['./department-table.component.scss']
})
export class DepartmentTableComponent {
  @Input() rows: DepartmentRow[] = [];
}
