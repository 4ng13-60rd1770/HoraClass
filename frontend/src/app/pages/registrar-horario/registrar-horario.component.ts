import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { CoursesSidebarComponent } from '../../organisms/courses-sidebar/courses-sidebar.component';
import { CourseItem } from '../../molecules/course-item/course-item.component';
import { CalendarEvent } from '../../organisms/schedule-calendar/schedule-calendar.component';
import { StudentService } from '../../core/services/student.service';
import { MateriasService, MateriaShared } from '../../core/services/materias.service';
import { StudentResponse } from '../../core/models/student.models';

// Paleta de colores para los cursos
const COLORS = [
  '#dcfce7', '#dbeafe', '#ede9fe', '#fef3c7',
  '#fce7f3', '#cffafe', '#ffedd5', '#d1fae5',
];

@Component({
  selector: 'app-registrar-horario',
  standalone: true,
  imports: [CommonModule, TopbarComponent, CoursesSidebarComponent],
  templateUrl: './registrar-horario.component.html',
  styleUrls: ['./registrar-horario.component.scss']
})
export class RegistrarHorarioComponent implements OnInit, OnDestroy {

  perfil: StudentResponse | null = null;
  courses: CourseItem[] = [];
  events: CalendarEvent[] = [];
  conflict: string | null = null;
  cargando = true;

  days  = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes'];
  hours = ['08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00'];

  private sub = new Subscription();

  constructor(
    private studentSvc: MateriasService,
    private profileSvc: StudentService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    // 1. Obtener perfil del estudiante para saber su carrera
    this.profileSvc.getProfile().subscribe({
      next: (p) => {
        this.perfil = p;
        this.cargando = false;
        // 2. Suscribirse a materias y filtrar por carrera del estudiante
        this.sub.add(
          this.studentSvc.materias$.subscribe(list => {
            this.courses = this.buildCourses(list, p.carrera);
            this.cdr.detectChanges();
          })
        );
      },
      error: () => {
        this.cargando = false;
        // Sin perfil: mostrar todas las que tengan docente y cupos
        this.sub.add(
          this.studentSvc.materias$.subscribe(list => {
            this.courses = this.buildCourses(list, null);
            this.cdr.detectChanges();
          })
        );
      }
    });
  }

  ngOnDestroy(): void { this.sub.unsubscribe(); }

  /** Filtra materias: misma carrera + docente asignado + cupos disponibles */
  private buildCourses(list: MateriaShared[], carrera: string | null): CourseItem[] {
    return list
      .filter(m =>
        (carrera === null || m.carrera === carrera) &&
        m.docente?.trim() &&
        m.cupoMax > 0 &&
        m.inscritos < m.cupoMax
      )
      .map((m, i) => ({
        code:      m.codigo || `MAT${m.id}`,
        name:      m.nombre,
        professor: m.docente,
        credits:   3,
        color:     COLORS[i % COLORS.length],
      }));
  }

  // ── Calendario ────────────────────────────────────────────────────────────
  getEventsForDay(dayIndex: number): CalendarEvent[] {
    return this.events.filter(e => e.day === dayIndex);
  }

  getTop(ev: CalendarEvent): number    { return (ev.startHour - 8) * 48; }
  getHeight(ev: CalendarEvent): number { return (ev.endHour - ev.startHour) * 48 - 4; }

  onDrop(event: DragEvent, dayIndex: number, hourIndex: number) {
    event.preventDefault();
    const data = event.dataTransfer?.getData('text/plain');
    if (!data) return;
    const course: CourseItem = JSON.parse(data);

    // Verificar si ya existe conflicto en esa hora
    const existing = this.events.find(
      e => e.day === dayIndex && !(8 + hourIndex + 1.5 <= e.startHour || 8 + hourIndex >= e.endHour)
    );
    if (existing) {
      this.conflict = course.name;
      return;
    }
    this.conflict = null;
    this.events.push({
      day:         dayIndex,
      startHour:   8 + hourIndex,
      endHour:     8 + hourIndex + 1.5,
      code:        course.code,
      name:        course.name,
      location:    `${String(8 + hourIndex).padStart(2,'0')}:00 – ${String(8 + hourIndex + 1).padStart(2,'0')}:30`,
      color:       course.color || '#dcfce7',
      borderColor: '#16a34a',
      textColor:   '#14532d',
    });
  }

  allowDrop(event: DragEvent) { event.preventDefault(); }

  removeEvent(ev: CalendarEvent) {
    this.events = this.events.filter(e => e !== ev);
  }
}
