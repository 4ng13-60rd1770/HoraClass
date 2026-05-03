import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-stat-card',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="stat-card">
      <div class="stat-card__content">
        <p class="stat-card__label">{{ label }}</p>
        <div class="stat-card__value-row">
          <span class="stat-card__value" [class]="valueClass">{{ value }}</span>
          <span *ngIf="valueExtra" class="stat-card__extra">{{ valueExtra }}</span>
        </div>
        <p *ngIf="subtitle" class="stat-card__subtitle">{{ subtitle }}</p>
      </div>
      <div *ngIf="svgIcon" class="stat-card__icon" [innerHTML]="safeSvg"></div>
      <div *ngIf="icon && !svgIcon" class="stat-card__icon">
        <img [src]="icon" [alt]="label" />
      </div>
      <div *ngIf="hasDonut" class="stat-card__donut">
        <svg width="60" height="60" viewBox="0 0 60 60">
          <circle cx="30" cy="30" r="24" fill="none" stroke="#e5e7eb" stroke-width="6"/>
          <circle cx="30" cy="30" r="24" fill="none"
            stroke="var(--color-primary)"
            stroke-width="6"
            [attr.stroke-dasharray]="donutDash + ' 150.8'"
            stroke-linecap="round"
            transform="rotate(-90 30 30)"/>
        </svg>
      </div>
    </div>
  `,
  styleUrls: ['./stat-card.component.scss']
})
export class StatCardComponent {
  @Input() label = '';
  @Input() value = '';
  @Input() valueExtra = '';
  @Input() subtitle = '';
  @Input() icon = '';
  @Input() svgIcon = '';
  @Input() valueClass = '';
  @Input() hasDonut = false;
  @Input() donutPercent = 0;

  constructor(private sanitizer: DomSanitizer) {}

  get safeSvg(): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(this.svgIcon);
  }

  get donutDash(): number {
    return (this.donutPercent / 100) * 150.8;
  }
}
