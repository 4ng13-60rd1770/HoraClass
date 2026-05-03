import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StatCardComponent } from '../../molecules/stat-card/stat-card.component';

export interface StatData {
  label: string;
  value: string;
  valueExtra?: string;
  subtitle?: string;
  icon?: string;
  svgIcon?: string;
  valueClass?: string;
  hasDonut?: boolean;
  donutPercent?: number;
}

@Component({
  selector: 'app-stats-row',
  standalone: true,
  imports: [CommonModule, StatCardComponent],
  template: `
    <div class="stats-row">
      <app-stat-card
        *ngFor="let s of stats"
        [label]="s.label"
        [value]="s.value"
        [valueExtra]="s.valueExtra || ''"
        [subtitle]="s.subtitle || ''"
        [icon]="s.icon || ''"
        [svgIcon]="s.svgIcon || ''"
        [valueClass]="s.valueClass || ''"
        [hasDonut]="s.hasDonut || false"
        [donutPercent]="s.donutPercent || 0"
      ></app-stat-card>
    </div>
  `,
  styleUrls: ['./stats-row.component.scss']
})
export class StatsRowComponent {
  @Input() stats: StatData[] = [];
}
