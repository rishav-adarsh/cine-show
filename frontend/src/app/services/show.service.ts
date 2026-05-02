import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseServerUrl from './helper';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ShowService {

  constructor(private http: HttpClient) { }

  addShow(show: any) {
    return this.http.post(`${baseServerUrl}/shows`, show).pipe(
      map((response: any) => response.data)
    );
  }

  fetchShowByShowId(showId: any) {
    return this.http.get(`${baseServerUrl}/shows/${showId}`).pipe(
      map((response: any) => response.data)
    );
  }

  bookMySeats(showId: any, showData: any) {
    return this.http.put(`${baseServerUrl}/shows/${showId}`, showData).pipe(
      map((response: any) => response.data)
    );
  }

}
