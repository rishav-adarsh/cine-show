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
  movies: any[] = [];
  filteredMovies: any[] = [];
  isLoading: boolean = false;
  searchTerm: string = '';
  minRating: number = 0;
  sortKey: 'name' | 'rating' | 'duration' = 'name';

  constructor(
    private movieService: MovieService
  ) { }

  ngOnInit(): void {
    this.isLoading = true;
    this.movieService.fetchMovies().subscribe(
      (data: any) => {
        console.log('movies:', data);
        this.movies = data;
        this.applyFilters();
        this.isLoading = false;
      },
      (err: any) => {
        this.isLoading = false;
        Swal.fire('Error!!', 'Error in loading movies', 'error');
      }
    );
  }

  applyFilters() {
    const query = this.searchTerm.trim().toLowerCase();
    const minRatingValue = Number(this.minRating) || 0;

    const filtered = this.movies.filter((movie) => {
      const name = String(movie.movieName || '').toLowerCase();
      const rating = Number(movie.rating) || 0;
      return name.includes(query) && rating >= minRatingValue;
    });

    this.filteredMovies = filtered.sort((a, b) => {
      if (this.sortKey === 'rating') {
        return (Number(b.rating) || 0) - (Number(a.rating) || 0);
      }
      if (this.sortKey === 'duration') {
        return (Number(a.duration) || 0) - (Number(b.duration) || 0);
      }
      return String(a.movieName || '').localeCompare(String(b.movieName || ''));
    });
  }

}
