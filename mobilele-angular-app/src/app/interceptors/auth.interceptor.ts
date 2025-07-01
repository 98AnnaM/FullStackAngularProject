import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import {Router} from '@angular/router';
import {catchError, throwError} from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token  = localStorage.getItem('token');
  const router = inject(Router);

  const request = token
    ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
    : req;

  return next(request).pipe(
    catchError(err => {
      if (err.status) {
        router.navigate(['/error', err.status.toString()]);
      }
      return throwError(() => err);
    })
  );
};
