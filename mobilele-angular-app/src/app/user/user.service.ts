import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthenticatedUser } from '../types/authenticated-user';
import { BehaviorSubject, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {
  private currentUser$$ = new BehaviorSubject<AuthenticatedUser | null>(null);
  currentUser$ = this.currentUser$$.asObservable();

  constructor(private http: HttpClient) {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.currentUser$$.next(JSON.parse(storedUser));
    }
  }

  get isLogged(): boolean {
    return !!this.currentUser$$.value;
  }

  get user(): AuthenticatedUser | null {
    return this.currentUser$$.value;
  }

  login(email: string, password: string) {
    return this.http
      .post<AuthenticatedUser>('http://localhost:8080/users/login', { email, password })
      .pipe(
        tap(user => {
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('token', user.token);
          this.currentUser$$.next(user);
        })
      );
  }

  logout(): void {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    this.currentUser$$.next(null);
  }
}
