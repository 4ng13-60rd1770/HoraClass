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

  usernameTouched = false;
  passwordTouched = false;

  constructor(private router: Router, private auth: AuthService) {}

  get isUsernameValid(): boolean {
    if (!this.username.trim()) return false;
    
    const emailOrUserRegex = /^(^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$)|(^[a-zA-Z0-9_]{4,15}$)$/;
    return emailOrUserRegex.test(this.username.trim());
  }

  get isPasswordValid(): boolean {
    return this.password.trim().length >= 6; 
  }

  get isFormInvalid(): boolean {
    return !this.isUsernameValid || !this.isPasswordValid || this.loading;
  }

  login() {
    this.usernameTouched = true;
    this.passwordTouched = true;

    if (this.isFormInvalid) return;

    this.loading = true;
    this.error = '';

    this.auth.login({ username: this.username.trim(), password: this.password.trim() }).subscribe({
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