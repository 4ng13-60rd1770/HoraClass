import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BadgeComponent, BadgeVariant } from '../../atoms/badge/badge.component';

export interface StudentRow {
  idNum: number;
  name: string;
  email: string;
  id: string;
  career: string;
  enrolledDate: string;
  statusVariant: BadgeVariant;
  status: string;
}

@Component({
  selector: 'app-students-table',
  standalone: true,
  imports: [CommonModule, BadgeComponent],
  template: `
    <div class="students-table-container">
      <table class="students-table">
        <thead>
          <tr>
            <th>Estudiante</th>
            <th>ID</th>
            <th>Carrera</th>
            <th>Matriculado</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          @for (row of rows; track row.idNum) {
            <tr class="students-table__row">
              <td class="students-table__student">
                <p class="students-table__name">{{ row.name }}</p>
                <p class="students-table__email">{{ row.email }}</p>
              </td>
              <td class="students-table__id">{{ row.id }}</td>
              <td>{{ row.career }}</td>
              <td>{{ row.enrolledDate }}</td>
              <td><app-badge [variant]="row.statusVariant">{{ row.status }}</app-badge></td>
              <td class="students-table__actions">
                <button class="action-btn action-btn--edit" title="Editar" (click)="editClick.emit(row.idNum)">
                  <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
                    <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
                  </svg>
                </button>
                <button class="action-btn action-btn--delete" title="Eliminar" (click)="deleteClick.emit(row.idNum)">
                  <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14H6L5 6"/>
                    <path d="M10 11v6"/><path d="M14 11v6"/>
                    <path d="M9 6V4h6v2"/>
                  </svg>
                </button>
              </td>
            </tr>
          }
        </tbody>
      </table>
    </div>
  `,
  styleUrls: ['./students-table.component.scss']
})
export class StudentsTableComponent {
  @Input() rows: StudentRow[] = [];
  @Output() editClick = new EventEmitter<number>();
  @Output() deleteClick = new EventEmitter<number>();
}
