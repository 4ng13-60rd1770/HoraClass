import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { InputComponent } from '../../atoms/input/input.component';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-recover-password',
  standalone: true,
  imports: [CommonModule, FormsModule, InputComponent],
  templateUrl: './recover-password.component.html',
  styleUrls: ['./recover-password.component.scss']
})
export class RecoverPasswordComponent {
  username = '';
  loading = false;
  error = '';
  successMessage = '';
  usernameTouched = false;

  constructor(private router: Router, private auth: AuthService) {}

  get isUsernameValid(): boolean {
    if (!this.username.trim()) return false;
    const emailOrUserRegex = /^([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}|\w{4,15})$/;
    return emailOrUserRegex.test(this.username.trim());
  }

  get isFormInvalid(): boolean {
    return !this.isUsernameValid || this.loading;
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }

  sendRecoveryEmail() {
    this.usernameTouched = true;
    if (this.isFormInvalid) return;

    this.loading = true;
    this.error = '';
    this.successMessage = '';

    this.auth.forgotPassword(this.username.trim()).subscribe({
      next: (response) => {
        this.loading = false;
        this.successMessage = response.message;
      },
      error: () => {
        this.loading = false;
        this.error = 'No se pudo procesar la solicitud. Inténtalo más tarde.';
      }
    });
  }
}
