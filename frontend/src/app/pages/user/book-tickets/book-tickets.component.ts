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
  shows: any[] = [];
  filteredShows: any[] = [];
  isLoading: boolean = false;
  movieId: any = null;
  theatreQuery: string = '';
  maxPrice: number = 0;

  constructor(
    private router: Router,
    private activeRoute: ActivatedRoute,
    private movieService: MovieService
  ) {}

  ngOnInit(): void {
    this.activeRoute.queryParamMap.subscribe((par) => {
      if (par.has('movieId')) {
        this.movieId = par.get('movieId');
        this.loadShows();
      }
    });
  }

  loadShows() {
    this.isLoading = true;
    this.movieService.fetchShowsByMovieId(this.movieId).subscribe(
      (data: any) => {
        this.shows = data;
        this.maxPrice = this.getMaxPrice();
        this.applyFilters();
        this.isLoading = false;
      },
      (error: any) => {
        this.isLoading = false;
        Swal.fire(
          'Error :(',
          'Error in loading shows from the movie!!',
          'error'
        );
      }
    );
  }

  getMaxPrice(): number {
    if (!this.shows.length) return 0;
    return Math.max(...this.shows.map((show) => Number(show.ticketPrice) || 0));
  }

  applyFilters() {
    const query = this.theatreQuery.trim().toLowerCase();
    const maxBudget = Number(this.maxPrice) || this.getMaxPrice();

    this.filteredShows = this.shows.filter((show) => {
      const theatreName = String(show?.theatre?.name || show?.theatre?.theatreName || '').toLowerCase();
      const location = String(show?.theatre?.location || '').toLowerCase();
      const price = Number(show?.ticketPrice) || 0;
      return (theatreName.includes(query) || location.includes(query)) && price <= maxBudget;
    });
  }

  seatsNavigation(show: any) {
    this.router.navigate(['/user/seats'], {
      queryParams: { showId: show.csid },
    });
  }
}
