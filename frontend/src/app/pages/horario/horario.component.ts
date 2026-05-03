import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { ScheduleCalendarComponent, CalendarEvent } from '../../organisms/schedule-calendar/schedule-calendar.component';

@Component({
  selector: 'app-horario',
  standalone: true,
  imports: [CommonModule, TopbarComponent, ScheduleCalendarComponent],
  templateUrl: './horario.component.html',
  styleUrls: ['./horario.component.scss']
})
export class HorarioComponent {
  events: CalendarEvent[] = [
    { day: 0, startHour: 9, endHour: 11, code: 'MAT202', name: 'Matemáticas II', location: 'Edificio, F-404', professor: 'Dr.Lizeth Bedoya', color: '#dbeafe', borderColor: '#4a7fcb', textColor: '#1e40af' },
    { day: 2, startHour: 9, endHour: 11, code: 'MAT202', name: 'Matemáticas II', location: 'Edificio, F-404', professor: 'Dr.Lizeth Bedoya', color: '#dbeafe', borderColor: '#4a7fcb', textColor: '#1e40af' },
    { day: 1, startHour: 9, endHour: 11, code: 'FS101', name: 'Física II', location: 'Lab GF-101', professor: 'Prof. Martin Lutero', color: '#dcfce7', borderColor: '#16a34a', textColor: '#14532d' },
    { day: 3, startHour: 9, endHour: 11, code: 'FS101', name: 'Física II', location: 'Lab GF-101', professor: 'Prof. Martin Lutero', color: '#dcfce7', borderColor: '#16a34a', textColor: '#14532d' },
    { day: 0, startHour: 13, endHour: 15, code: 'ARTL101', name: 'Arte de la literatura', location: 'Edificio I-303', color: '#fef9c3', borderColor: '#ca8a04', textColor: '#713f12' },
    { day: 2, startHour: 13, endHour: 15, code: 'ARTL101', name: 'Arte de la literatura', location: 'Edificio-I 303', color: '#fef9c3', borderColor: '#ca8a04', textColor: '#713f12' },
    { day: 4, startHour: 13, endHour: 15, code: 'ARTL101', name: 'Arte de la literatura', location: 'Edificio-I-103', color: '#fef9c3', borderColor: '#ca8a04', textColor: '#713f12' },
    { day: 4, startHour: 9, endHour: 11, code: 'ES105', name: 'Seminario de Investigación', location: 'Edificio-M-103', professor: 'Dr. Elena Kostic', color: '#ede9fe', borderColor: '#7c3aed', textColor: '#4c1d95' },
    { day: 1, startHour: 15, endHour: 17, code: 'LEG101', name: 'Legislación Y Normativa', location: 'Lecture Hall B', color: '#fce7f3', borderColor: '#db2777', textColor: '#831843' },
    { day: 3, startHour: 15, endHour: 17, code: 'LEG101', name: 'Legislacion y Normativa', location: 'Lecture Hall B', color: '#fce7f3', borderColor: '#db2777', textColor: '#831843' },
  ];
}
