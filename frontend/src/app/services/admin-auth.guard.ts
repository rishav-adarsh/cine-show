import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from './login.service';
import { inject } from '@angular/core';

export const adminAuthGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);
  const router = inject(Router);
  if (
    loginService.isLoggedIn() &&
    loginService.getActiveUserRole() === 'ADMIN'
  ) {
    return true;
  }
  router.navigate(['login']);
  return false;
};
