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
    startTime: null,
    startTimeTime: '10:00',
    endTime: null,
    endTimeTime: '13:00',
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
    if (!this.show.startTime || !this.show.endTime || !this.show.startTimeTime || !this.show.endTimeTime) {
      this.snackBar.open('All fields are Required!!', 'OK', {
        duration: 3000,
      });
      return;
    }

    // Combine date and time
    const combineDateTime = (date: Date, timeStr: string) => {
      const [hours, minutes] = timeStr.split(':').map(Number);
      const combined = new Date(date);
      combined.setHours(hours, minutes, 0, 0);
      return combined.toISOString(); // Backend expects ISO string or similar for LocalDateTime
    };

    const startTimeIso = combineDateTime(this.show.startTime, this.show.startTimeTime);
    const endTimeIso = combineDateTime(this.show.endTime, this.show.endTimeTime);

    // Flatten the show object to match backend DTO
    const showData = {
      startTime: startTimeIso,
      endTime: endTimeIso,
      ticketPrice: this.show.ticketPrice,
      movieId: this.show.movie.movieId,
      theatreId: this.show.theatre.theatreId
    };

    this.showService.addShow(showData).subscribe(
      (data: any) => {
        this.show.startTime = this.show.endTime = null;
        Swal.fire('Success :)', 'Show added successfully!!', 'success');
      },
      (err: any) => {
        Swal.fire('Error :(', 'Server Error !!', 'error');
      }
    );
  }
}
