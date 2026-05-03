import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BadgeComponent, BadgeVariant } from '../../atoms/badge/badge.component';

export interface ClassroomRow {
  idSalon: number;
  salonId: string;
  department: string;
  name: string;
  capacity: number;
  statusVariant: BadgeVariant;
  status: string;
}

@Component({
  selector: 'app-classrooms-table',
  standalone: true,
  imports: [CommonModule, BadgeComponent],
  template: `
    <div class="classrooms-table-container">
      <table class="classrooms-table">
        <thead>
          <tr>
            <th>Salon ID</th>
            <th>Salón</th>
            <th>Capacidad</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          @for (row of rows; track row.idSalon) {
            <tr class="classrooms-table__row">
              <td class="classrooms-table__id">
                <p class="classrooms-table__salon-id">{{ row.salonId }}</p>
                <p class="classrooms-table__dept">{{ row.department }}</p>
              </td>
              <td>{{ row.name }}</td>
              <td>{{ row.capacity }} Asientos</td>
              <td><app-badge [variant]="row.statusVariant">{{ row.status }}</app-badge></td>
              <td class="classrooms-table__actions">
                <button class="action-btn action-btn--edit" title="Editar" (click)="editClick.emit(row.idSalon)">
                  <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
                    <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
                  </svg>
                </button>
                <button class="action-btn action-btn--toggle" [title]="row.status === 'Libre' ? 'Marcar En Uso' : 'Marcar Libre'" (click)="toggleClick.emit(row.idSalon)">
                  <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M18.36 6.64A9 9 0 1 1 5.64 5.64"/>
                    <path d="M12 2v10"/>
                  </svg>
                </button>
                <button class="action-btn action-btn--delete" title="Eliminar" (click)="deleteClick.emit(row.idSalon)">
                  <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14H6L5 6"/>
                    <path d="M10 11v6"/><path d="M14 11v6"/><path d="M9 6V4h6v2"/>
                  </svg>
                </button>
              </td>
            </tr>
          }
        </tbody>
      </table>
    </div>
  `,
  styleUrls: ['./classrooms-table.component.scss']
})
export class ClassroomsTableComponent {
  @Input() rows: ClassroomRow[] = [];
  @Output() editClick   = new EventEmitter<number>();
  @Output() toggleClick = new EventEmitter<number>();
  @Output() deleteClick = new EventEmitter<number>();
}
