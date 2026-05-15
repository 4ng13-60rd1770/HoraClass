import { Routes } from '@angular/router';
import { authGuard, roleGuard } from './core/guards/auth.guard';
import { AuthLayoutComponent } from './templates/auth-layout/auth-layout.component';
import { MainLayoutComponent } from './templates/main-layout/main-layout.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardSecretariaComponent } from './pages/dashboard-secretaria/dashboard-secretaria.component';
import { CalendariosSecretariaComponent } from './pages/calendarios-secretaria/calendarios-secretaria.component';
import { SalonesComponent } from './pages/salones/salones.component';
import { ProfesoresComponent } from './pages/profesores/profesores.component';
import { EstudiantesComponent } from './pages/estudiantes/estudiantes.component';
import { DashboardEstudianteComponent } from './pages/dashboard-estudiante/dashboard-estudiante.component';
import { HorarioComponent } from './pages/horario/horario.component';
import { RegistrarHorarioComponent } from './pages/registrar-horario/registrar-horario.component';
import { InscripcionMateriasComponent } from './pages/inscripcion-materias/inscripcion-materias.component';
import { FranjasComponent } from './pages/franjas/franjas.component';
import { GenerarHorarioComponent } from './pages/generar-horario/generar-horario.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'login' },
  {
    path: '',
    component: AuthLayoutComponent,
    children: [{ path: 'login', component: LoginComponent }],
  },
  {
    path: 'secretaria',
    component: MainLayoutComponent,
    canActivate: [authGuard, roleGuard('ROLE_ADMIN')],
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
      { path: 'dashboard', component: DashboardSecretariaComponent },
      { path: 'calendarios', component: CalendariosSecretariaComponent },
      { path: 'salones', component: SalonesComponent },
      { path: 'profesores', component: ProfesoresComponent },
      { path: 'estudiantes', component: EstudiantesComponent },
      { path: 'franjas', component: FranjasComponent },
      { path: 'generar-horario', component: GenerarHorarioComponent },
    ],
  },
  {
    path: 'estudiante',
    component: MainLayoutComponent,
    canActivate: [authGuard, roleGuard('ROLE_ESTUDIANTE')],
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
      { path: 'dashboard', component: DashboardEstudianteComponent },
      { path: 'horario', component: HorarioComponent },
      { path: 'registrar-horario', component: RegistrarHorarioComponent },
    ],
  },
  {
    path: 'docente',
    component: MainLayoutComponent,
    canActivate: [authGuard, roleGuard('ROLE_DOCENTE')],
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'inscripcion' },
      { path: 'dashboard', pathMatch: 'full', redirectTo: 'inscripcion' },
      { path: 'inscripcion', component: InscripcionMateriasComponent },
    ],
  },
  { path: '**', redirectTo: 'login' },
];
