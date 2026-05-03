import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { InputComponent } from '../../atoms/input/input.component';
import { ButtonComponent } from '../../atoms/button/button.component';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, InputComponent, ButtonComponent],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username = '';
  password = '';
  loading = false;
  error = '';

  constructor(private router: Router, private auth: AuthService) {}

  login() {
    if (!this.username.trim() || !this.password.trim()) return;
    this.loading = true;
    this.error = '';

    this.auth.login({ username: this.username, password: this.password }).subscribe({
      next: (response) => {
        if (response.roles.includes('ROLE_ADMIN')) {
          this.router.navigate(['/secretaria/dashboard']);
        } else if (response.roles.includes('ROLE_DOCENTE')) {
          this.router.navigate(['/docente/inscripcion']);
        } else {
          this.router.navigate(['/estudiante/dashboard']);
        }
      },
      error: () => {
        this.error = 'Usuario o contraseña incorrectos';
        this.loading = false;
      }
    });
  }
}
