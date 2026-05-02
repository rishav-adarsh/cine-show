import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseServerUrl from './helper';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  constructor(private http: HttpClient) { }

  public fetchMovies() {
    return this.http.get(`${baseServerUrl}/movies`).pipe(
      map((response: any) => response.data)
    );
  }

  public addMovie(movie: any) {
    return this.http.post(`${baseServerUrl}/movies`, movie).pipe(
      map((response: any) => response.data)
    );
  }

  public fetchShowsByMovieId(movieId: any) {
    return this.http.get(`${baseServerUrl}/movies/${movieId}/shows`).pipe(
      map((response: any) => response.data)
    );
  }

}
