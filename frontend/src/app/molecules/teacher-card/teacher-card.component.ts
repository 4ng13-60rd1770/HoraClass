import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProgressBarComponent } from '../../atoms/progress-bar/progress-bar.component';

export interface Teacher {
  idProfesor: number;
  name: string;
  title: string;
  role: string;
  subjects: string[];
  loadHours: number;
  maxHours: number;
  avatarUrl?: string;
}

@Component({
  selector: 'app-teacher-card',
  standalone: true,
  imports: [CommonModule, ProgressBarComponent],
  template: `
    <div class="teacher-card">
      <div class="teacher-card__header">
        <div class="teacher-card__avatar">
          <img *ngIf="teacher.avatarUrl" [src]="teacher.avatarUrl" [alt]="teacher.name"/>
          <span *ngIf="!teacher.avatarUrl" class="teacher-card__initials">{{ initials }}</span>
        </div>
        <div class="teacher-card__info">
          <p class="teacher-card__name">{{ teacher.name }}</p>
          <p class="teacher-card__role">{{ teacher.role }}</p>
        </div>
      </div>
      <div class="teacher-card__body">
        <div class="teacher-card__tags">
          <span class="tag" *ngFor="let s of teacher.subjects">{{ s | uppercase }}</span>
        </div>
        <div class="teacher-card__load">
          <div class="teacher-card__load-label">
            <span>Carga Académica</span>
            <span class="teacher-card__load-val">{{ teacher.loadHours }}/{{ teacher.maxHours }} hrs</span>
          </div>
          <app-progress-bar [percent]="loadPercent" [color]="loadColor"></app-progress-bar>
        </div>
        <div class="teacher-card__actions">
          <button class="btn-edit" (click)="editClick.emit(teacher.idProfesor)" title="Editar">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
              <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
            </svg>
          </button>
          <button class="btn-delete" (click)="deleteClick.emit(teacher.idProfesor)" title="Eliminar">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14H6L5 6"/>
              <path d="M10 11v6"/><path d="M14 11v6"/>
              <path d="M9 6V4h6v2"/>
            </svg>
          </button>
        </div>
      </div>
    </div>
  `,
  styleUrls: ['./teacher-card.component.scss']
})
export class TeacherCardComponent {
  @Input() teacher!: Teacher;
  @Output() editClick = new EventEmitter<number>();
  @Output() deleteClick = new EventEmitter<number>();

  get initials(): string {
    return this.teacher.name.split(' ').map(w => w[0]).slice(0, 2).join('').toUpperCase();
  }

  get loadPercent(): number {
    return Math.round((this.teacher.loadHours / this.teacher.maxHours) * 100);
  }

  get loadColor(): string {
    const p = this.loadPercent;
    if (p >= 90) return '#e05555';
    if (p >= 60) return '#5a8a5e';
    return '#5a8a5e';
  }
}
