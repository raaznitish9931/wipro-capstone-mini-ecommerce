import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map, switchMap } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:9999';

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<User> {
    const loginBody = { email, password };

    return this.http.post(`${this.baseUrl}/user-w/login`, loginBody, { responseType: 'text' }).pipe(
      switchMap((response: string) => {
        if (!response.toLowerCase().includes('authentication')) {
          throw new Error('Authentication Failed');
        }

        return this.http.get<User[]>(`${this.baseUrl}/user-r`);
      }),
      map((users: User[]) => {
        // Filter users by exact email match
        const matchedUsers = users.filter(u => u.email?.toLowerCase() === email.toLowerCase());

        if (matchedUsers.length === 0) {
          throw new Error('User account not found');
        }

        // If multiple accounts exist (buggy data), we should be careful. 
        // For now, prioritize the role that was likely intended or log it.
        if (matchedUsers.length > 1) {
          console.warn('Multiple accounts found for this email. Data integrity issue suspected.');
        }

        const matchedUser = matchedUsers[0];
        localStorage.setItem('loggedInUser', JSON.stringify(matchedUser));
        return matchedUser;
      })
    );
  }

  register(user: User): Observable<any> {
    return this.http.post(`${this.baseUrl}/user-w`, user, { responseType: 'text' });
  }

  getLoggedInUser(): User | null {
    const user = localStorage.getItem('loggedInUser');
    return user ? JSON.parse(user) : null;
  }

  logout(): void {
    localStorage.removeItem('loggedInUser');
  }
}


