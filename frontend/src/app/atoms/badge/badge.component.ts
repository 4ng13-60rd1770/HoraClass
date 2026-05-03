import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

export type BadgeVariant = 'completado' | 'en-progreso' | 'atencion' | 'no-iniciado' | 'libre' | 'en-uso' | 'completo' | 'pendiente' | 'sin-asignacion';

@Component({
  selector: 'app-badge',
  standalone: true,
  imports: [CommonModule],
  template: `<span [class]="'badge badge--' + variant"><ng-content></ng-content></span>`,
  styleUrls: ['./badge.component.scss']
})
export class BadgeComponent {
  @Input() variant: BadgeVariant = 'completo';
}
