import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { CoursesSidebarComponent } from '../../organisms/courses-sidebar/courses-sidebar.component';
import { CourseItem } from '../../molecules/course-item/course-item.component';
import { CalendarEvent } from '../../organisms/schedule-calendar/schedule-calendar.component';

@Component({
  selector: 'app-registrar-horario',
  standalone: true,
  imports: [CommonModule, TopbarComponent, CoursesSidebarComponent],
  templateUrl: './registrar-horario.component.html',
  styleUrls: ['./registrar-horario.component.scss']
})
export class RegistrarHorarioComponent {
  conflict = 'MAT202';

  courses: CourseItem[] = [
    { code: 'FS101', name: 'Física II', professor: 'Prof. Martin Lutero', credits: 3, color: '#dcfce7' },
    { code: 'MAT202', name: 'Cálculo II', professor: 'Prof. Lizeth Bedoya', credits: 3, color: '#dbeafe' },
    { code: 'ES105', name: 'Seminario de Investigación', professor: 'Dr. Elena Kostic', credits: 3, color: '#ede9fe' },
    { code: 'CA205', name: 'Complejidad Algorítmica', professor: 'Prof. Wilson Rojas', credits: 3, color: '#fef3c7' },
    { code: 'ARTL101', name: 'Arte de la Literatura', professor: 'Prof. Ellen Marks', credits: 3, color: '#fef9c3' },
  ];

  days = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes'];
  hours = ['08:00 AM','09:00 AM','10:00 AM','11:00 AM','12:00 PM','13:00 PM','14:00 PM','15:00 PM','16:00 PM','17:00 PM','18:00 PM','19:00 PM'];

  events: CalendarEvent[] = [
    { day: 0, startHour: 9, endHour: 10.5, code: 'FS101', name: 'Física II', location: '9:00 - 10:30 AM', color: '#dcfce7', borderColor: '#16a34a', textColor: '#14532d' },
    { day: 2, startHour: 9, endHour: 10.5, code: 'FS101', name: 'Física II', location: '9:00 - 10:30 AM', color: '#dcfce7', borderColor: '#16a34a', textColor: '#14532d' },
    { day: 1, startHour: 10, endHour: 12, code: 'MAT202', name: 'Cálculo II', location: '10:00 - 12:00 PM', color: '#fee2e2', borderColor: '#dc2626', textColor: '#7f1d1d' },
  ];

  getEventsForDay(dayIndex: number): CalendarEvent[] {
    return this.events.filter(e => e.day === dayIndex);
  }

  getTop(ev: CalendarEvent): number {
    return (ev.startHour - 8) * 48;
  }

  getHeight(ev: CalendarEvent): number {
    return (ev.endHour - ev.startHour) * 48 - 4;
  }

  onDrop(event: DragEvent, dayIndex: number, hourIndex: number) {
    event.preventDefault();
    const data = event.dataTransfer?.getData('text/plain');
    if (data) {
      const course = JSON.parse(data);
      const colorMap: Record<string, string> = {
        FS101: '#dcfce7', MAT202: '#dbeafe', ES105: '#ede9fe',
        CA205: '#fef3c7', ARTL101: '#fef9c3'
      };
      this.events.push({
        day: dayIndex,
        startHour: 8 + hourIndex,
        endHour: 8 + hourIndex + 1.5,
        code: course.code,
        name: course.name,
        location: '',
        color: colorMap[course.code] || '#f0f0f0',
        borderColor: '#888',
        textColor: '#333'
      });
    }
  }

  allowDrop(event: DragEvent) { event.preventDefault(); }
}
