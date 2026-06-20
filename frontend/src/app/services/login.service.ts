import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseServerUrl from './helper';
import { Router } from '@angular/router';
import { BehaviorSubject, map, switchMap, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  loginStatusSubject = new BehaviorSubject<boolean>(this.isLoggedIn());
  loginStatusObservable = this.loginStatusSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  fetchActiveUser() {
    return this.http.get(`${baseServerUrl}/auth/current-user`).pipe(
      map((response: any) => response.data)
    );
  }

  // login user
  generateToken(loginUser: any) {
    return this.http.post(`${baseServerUrl}/auth/generate-token`, loginUser).pipe(
      map((response: any) => response.data)
    );
  }

  loginWithCredentials(credentials: { username: string; password: string }) {
    return this.generateToken(credentials).pipe(
      switchMap((data: any) => {
        this.setJwtToken(data.token);
        return this.fetchActiveUser();
      }),
      tap((user: any) => this.setActiveUser(user))
    );
  }

  setJwtToken(token: string) {
    localStorage.setItem('jwtToken', token);
    this.loginStatusSubject.next(true);
    return true;
  }

  getJwtToken() {
    return localStorage.getItem('jwtToken');
  }

  isLoggedIn() {
    let tokenStr = localStorage.getItem('jwtToken');
    if (!tokenStr) return false;
    return true;
  }

  logout() {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('user');
    this.loginStatusSubject.next(false);
    this.router.navigate(['login']);
    return true;
  }

  setActiveUser(user: any) {
    localStorage.setItem('user', JSON.stringify(user));
    return true;
  }

  getActiveUser() {
    let userStr = localStorage.getItem('user') as string;
    if (userStr) return JSON.parse(userStr);
    return null;
  }

  getActiveUserRole() {
    let user = this.getActiveUser();
    return user.roleId;
  }
}
