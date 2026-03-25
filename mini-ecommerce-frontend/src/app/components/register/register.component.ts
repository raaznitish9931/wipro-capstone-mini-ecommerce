import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  user: User = {
    name: '',
    email: '',
    password: '',
    role: '',
    address: ''
  };

  confirmPassword = '';
  errorMessage = '';
  successMessage = '';

  showPassword = false;
  showConfirmPassword = false;
  isLoading = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private http: HttpClient
  ) { }

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPassword(): void {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  register(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (!this.user.name || !this.user.email || !this.user.password || !this.user.role || !this.user.address) {
      this.errorMessage = 'Please fill all mandatory fields';
      return;
    }

    if (this.user.password !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match';
      return;
    }

    this.isLoading = true;

   
    this.http.get<User[]>('http://localhost:9999/user-r').subscribe({
      next: (users: User[]) => {
        const exists = users.some((u: User) => u.email?.toLowerCase() === this.user.email.toLowerCase());
        if (exists) {
          this.errorMessage = 'Email already registered. Please use another one.';
          this.isLoading = false;
          return;
        }

      
        this.executeRegistration();
      },
      error: () => this.executeRegistration() 
    });
  }

  private executeRegistration(): void {
    this.authService.register(this.user).subscribe({
      next: (response) => {
        console.log('Register Success:', response);
        this.isLoading = false;
        this.successMessage = 'Registration successful. Please login.';
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 1200);
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Register Error:', err);
        if (err.status === 0) {
          this.errorMessage = 'Server not reachable or CORS blocked';
        } else {
          this.errorMessage = 'Registration failed. Try again.';
        }
      }
    });
  }
}