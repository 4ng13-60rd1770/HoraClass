import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-nav-item',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <a [routerLink]="route" routerLinkActive="nav-item--active" class="nav-item">
      <span class="nav-item__icon" [innerHTML]="icon"></span>
      <span class="nav-item__label">{{ label }}</span>
    </a>
  `,
  styleUrls: ['./nav-item.component.scss']
})
export class NavItemComponent {
  @Input() label = '';
  @Input() icon = '';
  @Input() route = '/';
}
