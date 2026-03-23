import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  email = '';
  password = '';
  showPassword = false;
  errorMessage = '';
  isLoading = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  login(): void {
    this.errorMessage = '';

    if (!this.email || !this.password) {
      this.errorMessage = 'Please enter email and password';
      return;
    }

    this.isLoading = true;

    this.authService.login(this.email, this.password).subscribe({
      next: (user) => {
  this.isLoading = false;

  // ✅ YAHI ADD KARNA HAI
  sessionStorage.setItem('role', user.role);

  if (user.role?.toUpperCase() === 'ADMIN') {
    this.router.navigate(['/admin-dashboard']);
  } else {
    this.router.navigate(['/user-dashboard']);
  }
},
      error: (err) => {
        this.isLoading = false;
        console.error('Login Error:', err);

        if (err.status === 0) {
          this.errorMessage = 'Server not reachable or CORS blocked';
        } else {
          this.errorMessage = 'Invalid email or password';
        }
      }
    });
  }
}