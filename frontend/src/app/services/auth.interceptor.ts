import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { LoginService } from './login.service';
import Swal from 'sweetalert2';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private loginService: LoginService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let authReq = request;
    const token = this.loginService.getJwtToken();
    
    if (token && token !== 'undefined' && token !== 'null') {
      authReq = authReq.clone({
        setHeaders: { Authorization: `Bearer ${token}` },
      });
    }

    return next.handle(authReq).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error(`AuthInterceptor: Error ${error.status} for ${request.url}`, error);
        
        // Handle 401 Unauthorized or 403 Forbidden (often used for expired/invalid tokens)
        if (error.status === 401 || error.status === 403) {
          // If we're not already on the login page and it's not a login request
          if (!request.url.includes('/auth/generate-token')) {
            console.warn("AuthInterceptor: Session expired or unauthorized. Logging out...");
            
            this.loginService.logout();
            
            Swal.fire({
              title: 'Session Expired',
              text: 'Your session has expired. Please login again to continue.',
              icon: 'warning',
              confirmButtonText: 'Login'
            });
          }
        }
        return throwError(() => error);
      })
    );
  }
}

export const AuthInterceptorProviders = [
  {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true,
  },
];