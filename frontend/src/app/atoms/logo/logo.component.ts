import { Component } from '@angular/core';

@Component({
  selector: 'app-logo',
  standalone: true,
  template: `
    <div class="logo">
      <svg width="48" height="48" viewBox="0 0 48 48" fill="none">
        <path d="M24 4 C18 4 14 8 14 14 C14 18 16 22 20 24 C16 26 14 30 14 34 C14 40 18 44 24 44 C30 44 34 40 34 34 C34 30 32 26 28 24 C32 22 34 18 34 14 C34 8 30 4 24 4Z" fill="#e07b2a" opacity="0.9"/>
        <path d="M18 10 C18 10 20 6 24 6 C28 6 30 10 30 10" stroke="#c96a1a" stroke-width="1.5" fill="none"/>
        <circle cx="24" cy="24" r="3" fill="#fff" opacity="0.8"/>
      </svg>
      <div class="logo__text">
        <span class="logo__uni">UNIVERSIDAD</span>
        <span class="logo__name">EL BOSQUE</span>
      </div>
    </div>
  `,
  styleUrls: ['./logo.component.scss']
})
export class LogoComponent {}
