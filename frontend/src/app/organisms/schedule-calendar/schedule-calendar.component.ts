import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

export interface CalendarEvent {
  day: number;
  startHour: number;
  endHour: number;
  code: string;
  name: string;
  location: string;
  professor?: string;
  color: string;
  borderColor: string;
  textColor: string;
}

@Component({
  selector: 'app-schedule-calendar',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="calendar">
      <div class="calendar__grid">
        <div class="calendar__times">
          <div class="calendar__time-header"></div>
          <div *ngFor="let h of hours" class="calendar__time-slot">{{ h }}</div>
        </div>
        <div *ngFor="let day of days; let di = index" class="calendar__day-col">
          <div class="calendar__day-header">{{ day }}</div>
          <div class="calendar__day-body">
            <div *ngFor="let h of hours" class="calendar__cell"></div>
            <div *ngFor="let ev of getEventsForDay(di)"
              class="calendar__event"
              [style.background]="ev.color"
              [style.border-left]="'3px solid ' + ev.borderColor"
              [style.color]="ev.textColor"
              [style.top.px]="getTop(ev)"
              [style.height.px]="getHeight(ev)">
              <p class="calendar__event-code">{{ ev.code }}</p>
              <p class="calendar__event-name">{{ ev.name }}</p>
              <p class="calendar__event-loc">
                <svg width="10" height="10" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                  <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"/><circle cx="12" cy="10" r="3"/>
                </svg>
                {{ ev.location }}
              </p>
              <p *ngIf="ev.professor" class="calendar__event-prof">
                <svg width="10" height="10" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/>
                </svg>
                {{ ev.professor }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styleUrls: ['./schedule-calendar.component.scss']
})
export class ScheduleCalendarComponent {
  @Input() events: CalendarEvent[] = [];

  days = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes'];
  hours = ['08:00 AM','09:00 AM','10:00 AM','11:00 AM','12:00 PM','01:00 PM','02:00 PM','03:00 PM','04:00 PM','05:00 PM'];

  getEventsForDay(dayIndex: number): CalendarEvent[] {
    return this.events.filter(e => e.day === dayIndex);
  }

  getTop(ev: CalendarEvent): number {
    const startHourIndex = ev.startHour - 8;
    return startHourIndex * 52;
  }

  getHeight(ev: CalendarEvent): number {
    return (ev.endHour - ev.startHour) * 52 - 4;
  }
}
