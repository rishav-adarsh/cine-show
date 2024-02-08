import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseServerUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class ShowService {

  constructor(private http: HttpClient) { }

  addShow(show: any) {
    return this.http.post(`${baseServerUrl}/show`, show);
  }

  fetchShowByShowId(showId: any) {
    return this.http.get(`${baseServerUrl}/show/${showId}`);
  }

  bookMySeats(show: any) {
    return this.http.put(`${baseServerUrl}/show`, show);
  }

}
