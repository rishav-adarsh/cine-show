import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseServerUrl from './helper';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public createUser(user: any) {
    return this.http.post(`${baseServerUrl}/users`, user).pipe(
      map((response: any) => response.data)
    );
  }

  public updateUser(userId: string, user: any) {
    return this.http.put(`${baseServerUrl}/users/${userId}`, user).pipe(
      map((response: any) => response.data)
    );
  }
}
