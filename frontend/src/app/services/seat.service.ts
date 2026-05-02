import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseServerUrl from './helper';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SeatService {

  constructor(private http: HttpClient) { }

  public getSeatsByTheatre(theatreId: string) {
    return this.http.get(`${baseServerUrl}/seats/theatre/${theatreId}`).pipe(
      map((response: any) => response.data)
    );
  }

  public setupSeats(theatreId: string, setupRequest: any) {
    return this.http.post(`${baseServerUrl}/seats/theatre/${theatreId}/setup`, setupRequest).pipe(
      map((response: any) => response.data)
    );
  }
}
