import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MovieService } from 'src/app/services/movie.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-book-tickets',
  templateUrl: './book-tickets.component.html',
  styleUrls: ['./book-tickets.component.css'],
})
export class BookTicketsComponent implements OnInit {
  shows: any = [];
  movieId: any = null;

  constructor(
    private router: Router,
    private activeRoute: ActivatedRoute,
    private movieService: MovieService
  ) {}

  ngOnInit(): void {
    this.activeRoute.queryParamMap.subscribe((par) => {
      if (par.has('movieId')) {
        this.movieId = par.get('movieId');
      }
    });
    if (this.movieId) {
      this.movieService.fetchShowsByMovieId(this.movieId).subscribe(
        (data: any) => {
          this.shows = data;
        },
        (error: any) => {
          Swal.fire(
            'Error :(',
            'Error in loading shows from the movie!!',
            'error'
          );
        }
      );
    }
  }

  seatsNavigation(show: any) {
    this.router.navigate(['seats'], {
      queryParams: { showId: show.showId },
    });
  }
}
