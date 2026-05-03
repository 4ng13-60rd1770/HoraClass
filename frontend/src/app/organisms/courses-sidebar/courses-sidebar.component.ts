import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CourseItemComponent, CourseItem } from '../../molecules/course-item/course-item.component';

@Component({
  selector: 'app-courses-sidebar',
  standalone: true,
  imports: [CommonModule, FormsModule, CourseItemComponent],
  template: `
    <div class="courses-sidebar">
      <div class="courses-sidebar__search">
        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
        </svg>
        <input type="text" placeholder="Buscar Cursos" [(ngModel)]="query" class="courses-sidebar__input" />
      </div>

      <div class="courses-sidebar__filter">
        <select class="courses-sidebar__select">
          <option>Departamento</option>
          <option>Ingeniería</option>
          <option>Matemáticas</option>
        </select>
        <select class="courses-sidebar__select">
          <option>Todos</option>
        </select>
      </div>

      <p class="courses-sidebar__count">CURSOS ELEGIBLES ({{ courses.length }})</p>

      <div class="courses-sidebar__list">
        <app-course-item *ngFor="let c of filteredCourses" [item]="c" [draggable]="true"></app-course-item>
      </div>
    </div>
  `,
  styleUrls: ['./courses-sidebar.component.scss']
})
export class CoursesSidebarComponent {
  @Input() courses: CourseItem[] = [];
  query = '';

  get filteredCourses(): CourseItem[] {
    if (!this.query) return this.courses;
    return this.courses.filter(c =>
      c.name.toLowerCase().includes(this.query.toLowerCase()) ||
      c.code.toLowerCase().includes(this.query.toLowerCase())
    );
  }
}
