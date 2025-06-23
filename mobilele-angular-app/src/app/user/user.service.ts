import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Subscription, tap } from 'rxjs';
import { AuthenticatedUser } from '../types/authenticated-user';

@Injectable({ providedIn: 'root' })
export class UserService implements OnDestroy {
  private user$$ = new BehaviorSubject<AuthenticatedUser | null>(null);

  user: AuthenticatedUser | null = null;
  private userSubscription: Subscription;

  constructor(private http: HttpClient) {
    this.userSubscription = this.user$$.subscribe(user => {
      this.user = user;
    });

    const savedUser = localStorage.getItem('user');
    if (savedUser) {
      this.user$$.next(JSON.parse(savedUser));
    }
  }

  get isLogged(): boolean {
    return this.user !== null;
  }

  login(email: string, password: string) {
    return this.http
      .post<AuthenticatedUser>('http://localhost:8080/users/login', { email, password })
      .pipe(
        tap(user => {
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('token', user.token);
          this.user$$.next(user);
        })
      );
  }

  logout(): void {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    this.user$$.next(null);
  }

  register(firstName: string, lastName: string, email: string, password: any, confirmPassword: any) {
    return this.http.post('http://localhost:8080/users/register', { firstName, lastName, email, password, confirmPassword })
      .pipe(tap((result) => {}));
  }

  ngOnDestroy(): void {
    this.userSubscription.unsubscribe();
  }
}
