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

    this.movieService.addMovie(this.movie).subscribe(
      (data: any) => {
        this.movie.movieName = this.movie.releaseDate = this.movie.poster = '';
        this.movie.duration = 0;
        Swal.fire('Success :)', 'Movie added successfully!!', 'success');
      },
      (err: any) => {
        Swal.fire('Error :(', 'Server Error !!', 'error');  
      }
    )
  }

}
