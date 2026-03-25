import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ProductService } from '../../services/product.service';
import { User } from '../../models/user';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  user: User | null = null;
  isDropdownOpen = false;

  errorMessage = '';

  constructor(
    private authService: AuthService,
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getLoggedInUser();

    if (!this.user || this.user.role?.toUpperCase() !== 'ADMIN') {
      this.router.navigate(['/login']);
    } else {
      this.checkServiceHealth();
    }
  }

  checkServiceHealth(): void {
    this.productService.getAllProducts().subscribe({
      next: () => {
        this.errorMessage = '';
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Unable to load services. Please ensure microservices are running.';
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  get userRole(): string {
    if (!this.user || !this.user.role) return 'User';
    return this.user.role.toUpperCase() === 'ADMIN' ? 'Admin' : 'User';
  }

  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;
  }
}

