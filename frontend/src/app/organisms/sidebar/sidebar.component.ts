import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { NavItemComponent } from '../../molecules/nav-item/nav-item.component';
import { AvatarComponent } from '../../atoms/avatar/avatar.component';
import { LogoComponent } from '../../atoms/logo/logo.component';
import { AuthService } from '../../core/services/auth.service';

export interface NavLink {
  label: string;
  icon: string;
  route: string;
}

export interface SidebarUser {
  name: string;
  role: string;
  avatarUrl?: string;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule, NavItemComponent, AvatarComponent, LogoComponent],
  template: `
    <aside class="sidebar">
      <div class="sidebar__logo">
        <app-logo></app-logo>
      </div>

      <nav class="sidebar__nav">
        <app-nav-item
          *ngFor="let link of links"
          [label]="link.label"
          [icon]="link.icon"
          [route]="link.route"
        ></app-nav-item>
      </nav>

      <div class="sidebar__footer" (click)="toggleMenu()">
        <app-avatar [src]="user.avatarUrl || ''" [name]="user.name" [size]="36"></app-avatar>
        <div class="sidebar__user">
          <p class="sidebar__user-name">{{ user.name }}</p>
          <p class="sidebar__user-role">{{ user.role }}</p>
        </div>
        <div class="sidebar__context-menu" *ngIf="menuOpen" (click)="$event.stopPropagation()">
          <a class="ctx-item">Configuración</a>
          <a class="ctx-item ctx-item--danger" (click)="logout()">Logout</a>
        </div>
      </div>
    </aside>
  `,
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
  @Input() links: NavLink[] = [];
  @Input() user: SidebarUser = { name: '', role: '' };
  menuOpen = false;

  constructor(private auth: AuthService, private router: Router) {}

  toggleMenu() { this.menuOpen = !this.menuOpen; }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
