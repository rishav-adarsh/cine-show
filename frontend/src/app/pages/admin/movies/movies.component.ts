import { Component, OnInit } from '@angular/core';
import { MovieService } from 'src/app/services/movie.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css'],
})
export class MoviesComponent implements OnInit {
  movies: any = [];

  constructor(private movieServie: MovieService) { }

  ngOnInit(): void {
    this.movieServie.fetchMovies().subscribe(
      (data: any) => {
        console.log('movies:', data);
        this.movies = data;
      },
      (err: any) => {
        Swal.fire('Error!!', 'Error in loading movies', 'error');
      }
    )
  }
}
