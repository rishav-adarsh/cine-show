import { Component, OnInit } from '@angular/core';
import { MovieService } from 'src/app/services/movie.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css'],
})
export class MoviesComponent implements OnInit {
  movies: any[] = [];
  filteredMovies: any[] = [];
  isLoading: boolean = false;
  searchTerm: string = '';
  minRating: number = 0;

  constructor(private movieServie: MovieService) { }

  ngOnInit(): void {
    this.isLoading = true;
    this.movieServie.fetchMovies().subscribe(
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
    )
  }

  applyFilters() {
    const query = this.searchTerm.trim().toLowerCase();
    const minRating = Number(this.minRating) || 0;

    this.filteredMovies = this.movies
      .filter((movie) => {
        const movieName = String(movie.movieName || '').toLowerCase();
        const rating = Number(movie.rating) || 0;
        return movieName.includes(query) && rating >= minRating;
      })
      .sort((a, b) => String(a.movieName || '').localeCompare(String(b.movieName || '')));
  }
}
