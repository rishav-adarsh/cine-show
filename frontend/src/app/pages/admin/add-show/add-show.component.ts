import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MovieService } from 'src/app/services/movie.service';
import { ShowService } from 'src/app/services/show.service';
import { TheatreService } from 'src/app/services/theatre.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-show',
  templateUrl: './add-show.component.html',
  styleUrls: ['./add-show.component.css'],
})
export class AddShowComponent implements OnInit {
  show: any = {
    startTime: '',
    endTime: '',
    ticketPrice: 0,
    movie: {
      movieId: -1,
    },
    theatre: {
      theatreId: -1,
    },
  };

  movies: any = [];
  theatres: any = [];

  constructor(
    private showService: ShowService,
    private movieService: MovieService,
    private theatreService: TheatreService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.movieService.fetchMovies().subscribe(
      (data: any) => {
        this.movies = data;
      },
      (err: any) => {
        Swal.fire('Error :(', 'Error in loading movies from server!!', 'error');
      }
    );

    this.theatreService.fetchTheatres().subscribe(
      (data: any) => {
        this.theatres = data;
      },
      (err: any) => {
        Swal.fire(
          'Error :(',
          'Error in loading theatres from server!!',
          'error'
        );
      }
    );
  }

  onSubmit() {
    if (!this.show.startTime || !this.show.endTime) {
      this.snackBar.open('All fields are Required!!', 'OK', {
        duration: 3000,
      });
      return;
    }

    this.showService.addShow(this.show).subscribe(
      (data: any) => {
        this.show.startTime = this.show.endTime = '';
        Swal.fire('Success :)', 'Show added successfully!!', 'success');
      },
      (err: any) => {
        Swal.fire('Error :(', 'Server Error !!', 'error');
      }
    );
  }
}
