import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from './login.service';

export const userAuthGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);
  const router = inject(Router);
  if (
    loginService.isLoggedIn() &&
    loginService.getActiveUserRole() === 'NORMAL'
  ) {
    return true;
  }
  router.navigate(['login']);
  return false;
};
