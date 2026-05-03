import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-avatar',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="avatar" [style.width.px]="size" [style.height.px]="size">
      <img *ngIf="src" [src]="src" [alt]="alt" />
      <span *ngIf="!src" class="avatar__initials">{{ initials }}</span>
    </div>
  `,
  styleUrls: ['./avatar.component.scss']
})
export class AvatarComponent {
  @Input() src = '';
  @Input() alt = '';
  @Input() size = 36;
  @Input() name = '';

  get initials(): string {
    return this.name.split(' ').map(w => w[0]).slice(0, 2).join('').toUpperCase();
  }
}
