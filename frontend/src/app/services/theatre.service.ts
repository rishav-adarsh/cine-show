import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseServerUrl from './helper';

@Injectable({
  providedIn: 'root',
})
export class TheatreService {
  constructor(private http: HttpClient) {}

  public fetchTheatres() {
    return this.http.get(`${baseServerUrl}/theatre`);
  }

  public addTheatre(theatre: any) {
    return this.http.post(`${baseServerUrl}/theatre`, theatre);
  }
}
