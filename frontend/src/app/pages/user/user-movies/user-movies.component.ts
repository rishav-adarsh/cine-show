import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { MovieService } from 'src/app/services/movie.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-movies',
  templateUrl: './user-movies.component.html',
  styleUrls: ['./user-movies.component.css']
})
export class UserMoviesComponent implements OnInit {
  
  movies: any = [];

  constructor(
    private movieService: MovieService
  ) { }

  ngOnInit(): void {
    this.movies = this.movieService.fetchMovies().subscribe(
      (data: any) => {
        console.log('movies:', data);
        this.movies = data;
      },
      (err: any) => {
        Swal.fire('Error!!', 'Error in loading movies', 'error');
      }
    );
  }

}
