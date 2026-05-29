import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { InputComponent } from '../../atoms/input/input.component';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [CommonModule, FormsModule, InputComponent],
  templateUrl: './reset-password.component.html',
  styleUrls: [
    '../recovery-password/recover-password.component.scss',
    './reset-password.component.scss'
  ]
})
export class ResetPasswordComponent implements OnInit {
  token = '';
  newPassword = '';
  confirmPassword = '';
  loading = false;
  error = '';
  successMessage = '';
  passwordTouched = false;
  confirmTouched = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private auth: AuthService
  ) {}

  ngOnInit() {
    this.token = this.route.snapshot.queryParamMap.get('token') ?? '';
    if (!this.token) {
      this.error = 'El enlace de recuperación no es válido.';
    }
  }

  get isPasswordValid(): boolean {
    return this.newPassword.trim().length >= 6;
  }

  get isConfirmValid(): boolean {
    return this.confirmPassword.trim() === this.newPassword.trim() && this.isPasswordValid;
  }

  get isFormInvalid(): boolean {
    return !this.token || !this.isConfirmValid || this.loading;
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }

  resetPassword() {
    this.passwordTouched = true;
    this.confirmTouched = true;
    if (this.isFormInvalid) return;

    this.loading = true;
    this.error = '';
    this.successMessage = '';

    this.auth.resetPassword({ token: this.token, newPassword: this.newPassword.trim() }).subscribe({
      next: (response) => {
        this.loading = false;
        this.successMessage = response.message;
      },
      error: (err) => {
        this.loading = false;
        this.error = err?.error?.message ?? 'No se pudo restablecer la contraseña.';
      }
    });
  }
}
