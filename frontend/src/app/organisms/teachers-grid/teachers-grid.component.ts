import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TeacherCardComponent, Teacher } from '../../molecules/teacher-card/teacher-card.component';

@Component({
  selector: 'app-teachers-grid',
  standalone: true,
  imports: [CommonModule, TeacherCardComponent],
  template: `
    <div class="teachers-grid">
      @for (t of teachers; track t.idProfesor) {
        <app-teacher-card
          [teacher]="t"
          (editClick)="editClick.emit($event)"
          (deleteClick)="deleteClick.emit($event)">
        </app-teacher-card>
      }
    </div>
  `,
  styleUrls: ['./teachers-grid.component.scss']
})
export class TeachersGridComponent {
  @Input() teachers: Teacher[] = [];
  @Output() editClick = new EventEmitter<number>();
  @Output() deleteClick = new EventEmitter<number>();
}
