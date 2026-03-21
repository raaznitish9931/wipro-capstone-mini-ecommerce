import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const user = authService.getLoggedInUser();

  if (!user) {
    router.navigate(['/login']);
    return false;
  }

  // Check role-based access
  const path = state.url;
  const role = user.role?.toUpperCase();

  if (path.includes('admin') && role !== 'ADMIN') {
    router.navigate(['/user-dashboard']);
    return false;
  }

  if (path.includes('user-dashboard') && role === 'ADMIN') {
    router.navigate(['/admin-dashboard']);
    return false;
  }

  return true;
};
