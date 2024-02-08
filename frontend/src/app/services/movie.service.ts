import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseServerUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  constructor(private http: HttpClient) { }

  public fetchMovies() {
    return this.http.get(`${baseServerUrl}/movie`);
  }

  public addMovie(movie: any) {
    return this.http.post(`${baseServerUrl}/movie`, movie);
  }

  public fetchShowsByMovieId(movieId: any) {
    return this.http.get(`${baseServerUrl}/movie/${movieId}/shows`);
  }

}
