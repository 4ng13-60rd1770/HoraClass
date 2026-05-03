import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

export interface CourseItem {
  code: string;
  name: string;
  professor: string;
  credits: number;
  color?: string;
}

@Component({
  selector: 'app-course-item',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="course-item" [class.course-item--draggable]="draggable" [draggable]="draggable" (dragstart)="onDragStart($event)">
      <span class="course-item__code" [style.background]="item.color || '#e8f5e9'" [style.color]="'#333'">{{ item.code }}</span>
      <span class="course-item__credits">{{ item.credits }} Créditos</span>
      <p class="course-item__name">{{ item.name }}</p>
      <p class="course-item__prof">{{ item.professor }}</p>
    </div>
  `,
  styleUrls: ['./course-item.component.scss']
})
export class CourseItemComponent {
  @Input() item!: CourseItem;
  @Input() draggable = false;
  @Output() dragStarted = new EventEmitter<CourseItem>();

  onDragStart(event: DragEvent) {
    this.dragStarted.emit(this.item);
    event.dataTransfer?.setData('text/plain', JSON.stringify(this.item));
  }
}
