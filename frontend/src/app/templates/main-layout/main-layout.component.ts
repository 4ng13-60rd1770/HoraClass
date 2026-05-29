import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet } from '@angular/router';
import { SidebarComponent, NavLink, SidebarUser } from '../../organisms/sidebar/sidebar.component';
import { AuthService } from '../../core/services/auth.service';

const ICON_DASHBOARD = `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/><rect x="14" y="14" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/></svg>`;
const ICON_CALENDAR = `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>`;
const ICON_ROOMS = `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="2" y="7" width="20" height="14" rx="2" ry="2"/><path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"/></svg>`;
const ICON_TEACHERS = `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>`;
const ICON_STUDENTS = `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>`;
const ICON_SCHEDULE = `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/></svg>`;
const ICON_GENERATE = `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polygon points="13 2 3 14 12 14 11 22 21 10 12 10 13 2"/></svg>`;

const ICON_SETTINGS = `<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83-2.83l.06-.06A1.65 1.65 0 0 0 4.68 15a1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 2.83-2.83l.06.06A1.65 1.65 0 0 0 9 4.68a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 2.83l-.06.06A1.65 1.65 0 0 0 19.4 9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z"/></svg>`;

const NAV_SECRETARIA: NavLink[] = [
  { label: 'Dashboard', icon: ICON_DASHBOARD, route: '/secretaria/dashboard' },
  { label: 'Calendarios', icon: ICON_CALENDAR, route: '/secretaria/calendarios' },
  { label: 'Salones', icon: ICON_ROOMS, route: '/secretaria/salones' },
  { label: 'Profesores', icon: ICON_TEACHERS, route: '/secretaria/profesores' },
  { label: 'Estudiantes', icon: ICON_STUDENTS, route: '/secretaria/estudiantes' },
  { label: 'Reglas Académicas', icon: ICON_SETTINGS, route: '/secretaria/reglas-academicas' },
  { label: 'Generar Horario', icon: ICON_GENERATE, route: '/secretaria/generar-horario' },
];

const NAV_ESTUDIANTE: NavLink[] = [
  { label: 'Dashboard', icon: ICON_DASHBOARD, route: '/estudiante/dashboard' },
  { label: 'Calendarios', icon: ICON_CALENDAR, route: '/estudiante/horario' },
  { label: 'Registrar Horario', icon: ICON_SCHEDULE, route: '/estudiante/registrar-horario' },
];

const NAV_DOCENTE: NavLink[] = [
  { label: 'Dashboard', icon: ICON_DASHBOARD, route: '/docente/dashboard' },
  { label: 'Inscripción', icon: ICON_SCHEDULE, route: '/docente/inscripcion' },
];

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, SidebarComponent],
  template: `
    <div class="main-layout">
      <app-sidebar [links]="links" [user]="user"></app-sidebar>
      <main class="main-layout__content">
        <router-outlet></router-outlet>
      </main>
    </div>
  `,
  styleUrls: ['./main-layout.component.scss']
})
export class MainLayoutComponent implements OnInit {
  links: NavLink[] = [];
  user: SidebarUser = { name: '', role: '' };

  constructor(private router: Router, private auth: AuthService) {}

  ngOnInit() {
    const storedUser = this.auth.getUser();
    const username = storedUser?.username ?? 'Usuario';
    const roles = storedUser?.roles ?? [];

    const url = this.router.url;
    if (url.includes('/secretaria')) {
      this.links = NAV_SECRETARIA;
      this.user = { name: username, role: 'Administrativo' };
    } else if (url.includes('/estudiante')) {
      this.links = NAV_ESTUDIANTE;
      this.user = { name: username, role: 'Estudiante' };
    } else if (url.includes('/docente')) {
      this.links = NAV_DOCENTE;
      this.user = { name: username, role: 'Docente' };
    } else {
      this.user = { name: username, role: roles[0] ?? '' };
    }
  }
}
