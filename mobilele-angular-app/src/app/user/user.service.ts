import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, catchError, delay, delayWhen, of, Subscription, tap, throwError } from 'rxjs';
import { UserLoginResponse } from '../types/userLoginResponse';
import {UserRegisterRequest} from '../types/userRegisterRequest';
import {UserLoginRequest} from '../types/userLoginRequest';

@Injectable({ providedIn: 'root' })
export class UserService implements OnDestroy {
  private user$$ = new BehaviorSubject<UserLoginResponse | null>(null);

  user: UserLoginResponse | null = null;
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

  login(userLoginRequest: UserLoginRequest) {
    return this.http
      .post<UserLoginResponse>('http://localhost:8080/users/login', userLoginRequest)
      .pipe(
        delay(2000),
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

  register(userRegisterRequest: UserRegisterRequest) {
    return this.http.post('http://localhost:8080/users/register', userRegisterRequest)
      .pipe(
      catchError(err => throwError(() => err)),
      delayWhen(() => of(null).pipe(delay(4000)))
    );
  }

  ngOnDestroy(): void {
    this.userSubscription.unsubscribe();
  }
}
