import { HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');

  const request = token
    ? req.clone({setHeaders: {Authorization: `Bearer ${token}`}})
    : req;

  return next(request).pipe(
    catchError(err => {
      return throwError(() => err);
    })
  );
};
