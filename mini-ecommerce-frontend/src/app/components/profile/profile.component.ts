import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, NavbarComponent],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: User | null = null;
  isEditMode = false;
  
  oldPasswordText = '';
  newPasswordText = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getLoggedInUser();
    if (!this.user) {
      this.router.navigate(['/login']);
      return;
    }

    this.route.queryParams.subscribe(params => {
      this.isEditMode = params['edit'] === 'true';
    });
  }

  saveProfile(): void {
    if (this.user) {
      if (this.newPasswordText) {
        this.user.password = this.newPasswordText;
      }
      
      sessionStorage.setItem('loggedInUser', JSON.stringify(this.user));
      alert("Profile updated successfully!");
      this.isEditMode = false;
      
      const role = this.user?.role?.toUpperCase();
      if (role === 'ADMIN') {
        this.router.navigate(['/admin-dashboard']);
      } else {
        this.router.navigate(['/user-dashboard']);
      }
    }
  }

  goBack(): void {
    const role = this.user?.role?.toUpperCase();
    if (role === 'ADMIN') {
      this.router.navigate(['/admin-dashboard']);
    } else {
      this.router.navigate(['/user-dashboard']);
    }
  }
}
