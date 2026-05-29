import { Component } from '@angular/core';

@Component({
  selector: 'app-logo',
  standalone: true,
  template: `
    <div class="logo">
            <img
        src="https://images.squarespace-cdn.com/content/v1/5f46720407ab6957e16cb0c5/340ef60e-0fdf-4d0b-96d4-8a9a01d966b2/Logo_de_la_Universidad_El_Bosque.svg.png"
        alt="Logo Universidad El Bosque"
        class="logo__img"
        width="48"
        height="48"
      />
      <div class="logo__text">
        <span class="logo__uni">UNIVERSIDAD</span>
        <span class="logo__name">EL BOSQUE</span>
      </div>
    </div>
  `,
  styleUrls: ['./logo.component.scss']
})
export class LogoComponent {}
