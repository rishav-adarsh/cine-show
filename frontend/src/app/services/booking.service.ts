import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseServerUrl from './helper';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private http: HttpClient) { }

  createBooking(booking: any) {
    return this.http.post(`${baseServerUrl}/bookings`, booking).pipe(
      map((response: any) => response.data)
    );
  }

  getBooking(id: string) {
    return this.http.get(`${baseServerUrl}/bookings/${id}`).pipe(
      map((response: any) => response.data)
    );
  }

  getBookingsByUserId(userId: string) {
    return this.http.get(`${baseServerUrl}/bookings/user/${userId}`).pipe(
      map((response: any) => response.data)
    );
  }
}
