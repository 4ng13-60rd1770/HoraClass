import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

export interface ClassBlock {
  code: string;
  name: string;
  location: string;
  professor?: string;
  status?: 'en-curso' | 'sin-comenzar' | '';
  color?: string;
  borderColor?: string;
}

@Component({
  selector: 'app-class-block',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="class-block" [style.background]="block.color || '#f0f7f0'" [style.border-left-color]="block.borderColor || 'var(--color-primary)'">
      <div class="class-block__main">
        <p class="class-block__name">{{ block.name }}</p>
        <div class="class-block__meta">
          <span class="class-block__loc">
            <svg width="11" height="11" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"/><circle cx="12" cy="10" r="3"/></svg>
            {{ block.location }}
          </span>
          <span *ngIf="block.professor" class="class-block__prof">
            <svg width="11" height="11" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>
            {{ block.professor }}
          </span>
        </div>
      </div>
      <span *ngIf="block.status" class="class-block__status" [class]="'status--' + block.status">
        {{ block.status === 'en-curso' ? 'EN CURSO' : 'SIN COMENZAR' }}
      </span>
    </div>
  `,
  styleUrls: ['./class-block.component.scss']
})
export class ClassBlockComponent {
  @Input() block!: ClassBlock;
}
