import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchBarComponent } from '../../molecules/search-bar/search-bar.component';
import { ButtonComponent } from '../../atoms/button/button.component';

@Component({
  selector: 'app-topbar',
  standalone: true,
  imports: [CommonModule, SearchBarComponent, ButtonComponent],
  template: `
    <header class="topbar">
      <div class="topbar__search">
        <app-search-bar [placeholder]="searchPlaceholder"></app-search-bar>
      </div>
      <div class="topbar__actions">
        <button class="topbar__bell">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
            <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/>
            <path d="M13.73 21a2 2 0 0 1-3.46 0"/>
          </svg>
        </button>
        <button *ngIf="actionLabel" class="topbar__action-btn" [class]="'topbar__action-btn--' + actionVariant" (click)="actionClick.emit()">
          {{ actionLabel }}
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
            <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
        </button>
      </div>
    </header>
  `,
  styleUrls: ['./topbar.component.scss']
})
export class TopbarComponent {
  @Input() searchPlaceholder = 'Buscar...';
  @Input() actionLabel = '';
  @Input() actionVariant: 'default' | 'pink' = 'default';
  @Output() actionClick = new EventEmitter<void>();
}
