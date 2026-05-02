import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseServerUrl from './helper';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TheatreService {
  constructor(private http: HttpClient) {}

  public fetchTheatres() {
    return this.http.get(`${baseServerUrl}/theatres`).pipe(
      map((response: any) => response.data)
    );
  }

  public addTheatre(theatre: any) {
    return this.http.post(`${baseServerUrl}/theatres`, theatre).pipe(
      map((response: any) => response.data)
    );
  }
}
