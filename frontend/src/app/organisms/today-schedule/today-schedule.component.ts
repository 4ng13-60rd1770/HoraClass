import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassBlockComponent, ClassBlock } from '../../molecules/class-block/class-block.component';

export interface ScheduleEntry {
  time: string;
  block: ClassBlock;
}

@Component({
  selector: 'app-today-schedule',
  standalone: true,
  imports: [CommonModule, ClassBlockComponent],
  template: `
    <div class="today-schedule">
      <div class="today-schedule__header">
        <div class="today-schedule__title">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
            <line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/>
            <line x1="3" y1="10" x2="21" y2="10"/>
          </svg>
          <span>Horario para hoy</span>
        </div>
        <a class="today-schedule__calendar-link" routerLink="/horario">Ver Calendario</a>
      </div>

      <div class="today-schedule__list">
        <div *ngFor="let entry of entries" class="today-schedule__entry">
          <div class="today-schedule__time">
            <p class="today-schedule__hour">{{ entry.time }}</p>
            <p class="today-schedule__ampm">AM</p>
          </div>
          <app-class-block [block]="entry.block" class="today-schedule__block"></app-class-block>
        </div>
        <div *ngIf="entries.length === 0" class="today-schedule__empty">
          No hay más clase- ¡Disfruta el Descanso!
        </div>
        <div class="today-schedule__end">
          No hay más clase- ¡Disfruta el Descanso!
        </div>
      </div>
    </div>
  `,
  styleUrls: ['./today-schedule.component.scss']
})
export class TodayScheduleComponent {
  @Input() entries: ScheduleEntry[] = [];
}
