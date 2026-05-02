import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MovieService } from 'src/app/services/movie.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-movie',
  templateUrl: './add-movie.component.html',
  styleUrls: ['./add-movie.component.css']
})
export class AddMovieComponent {

  movie: any = {
    movieName: '',
    releaseDate: '',
    duration: 0,
    poster: '',
  };

  constructor(private movieService: MovieService, private snackBar: MatSnackBar) { }

  onSubmit() {
    if (!this.movie.movieName) {
      this.snackBar.open('Title is Required!!', 'OK', {
        duration: 3000,
      });
      return;
    }

    // Convert Date object to YYYY-MM-DD string for backend LocalDate
    const movieData = { ...this.movie };
    if (movieData.releaseDate instanceof Date) {
      movieData.releaseDate = movieData.releaseDate.toISOString().split('T')[0];
    }

    this.movieService.addMovie(movieData).subscribe(
      (data: any) => {
        this.movie = {
          movieName: '',
          releaseDate: '',
          duration: 0,
          poster: '',
        };
        Swal.fire('Success :)', 'Movie added successfully!!', 'success');
      },
      (err: any) => {
        Swal.fire('Error :(', 'Server Error !!', 'error');
      }
    );
  }

}
