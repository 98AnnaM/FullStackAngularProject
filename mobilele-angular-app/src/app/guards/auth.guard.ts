import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { UserService } from '../user/user.service';


export const AuthGuard: CanActivateFn = () => {
  const userService = inject(UserService);
  const router = inject(Router);

  return userService.isLogged
    ? true
    : router.createUrlTree(['/users/login']);
};
