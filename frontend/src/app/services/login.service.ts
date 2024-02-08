import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseServerUrl from './helper';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  // loginStatusSubject = new BehaviorSubject<boolean>(false);
  // loginStatusObservable = this.loginStatusSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  fetchActiveUser() {
    return this.http.get(`${baseServerUrl}/current-user`);
  }

  // login user
  generateToken(loginUser: any) {
    return this.http.post(`${baseServerUrl}/generate-token`, loginUser);
    // this.loginStatusSubject.next(true); // should be done after successful post request
    // so will be emitting the value change in login.component.ts
  }

  setJwtToken(token: string) {
    localStorage.setItem('jwtToken', token);
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
    // this.loginStatusSubject.next(false);
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
    this.logout();
    return null;
  }

  getActiveUserRole() {
    let user = this.getActiveUser();
    return user.authorities[0].authority;
  }
}
