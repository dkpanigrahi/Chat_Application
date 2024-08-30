import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { LoginService } from './login.service'; // Adjust the path as needed

export const authGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);
  
  if (loginService.isLoggedIn()) {
    return true;
  } else {
    const router = inject(Router);
    router.navigate(['/login']);
    return false;
  }
};
