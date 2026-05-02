import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private loginService: LoginService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let authReq = request;
    const token = this.loginService.getJwtToken();
    console.log("AuthInterceptor: intercepting request to", request.url);
    
    if (token && token !== 'undefined' && token !== 'null') {
      console.log("AuthInterceptor: adding token to header");
      authReq = authReq.clone({
        setHeaders: { Authorization: `Bearer ${token}` },
      });
    } else {
      console.log("AuthInterceptor: no valid token found");
    }
    return next.handle(authReq);
  }
}

export const AuthInterceptorProviders = [
  {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true,
  },
];