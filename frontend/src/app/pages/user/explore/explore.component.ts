import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MovieService } from 'src/app/services/movie.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['./explore.component.css'],
})
export class ExploreComponent implements OnInit, OnDestroy {
  isLoading: boolean = false;
  movies: any[] = [];
  featuredMovies: any[] = [];
  activeSlideIndex: number = 0;
  readonly genreChips: string[] = ['Action', 'Drama', 'Thriller', 'Romance', 'Sci-Fi', 'Family'];
  private intervalId: any = null;

  constructor(private movieService: MovieService, private router: Router) {}

  ngOnInit(): void {
    this.loadMovies();
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  loadMovies() {
    this.isLoading = true;
    this.movieService.fetchMovies().subscribe(
      (data: any) => {
        this.movies = Array.isArray(data) ? data : [];
        this.featuredMovies = [...this.movies]
          .sort((a, b) => (Number(b.rating) || 0) - (Number(a.rating) || 0))
          .slice(0, 5);
        this.startSlider();
        this.isLoading = false;
      },
      () => {
        this.isLoading = false;
        Swal.fire('Error!', 'Unable to load movies for explore page.', 'error');
      }
    );
  }

  startSlider() {
    if (this.intervalId || this.featuredMovies.length <= 1) {
      return;
    }
    this.intervalId = setInterval(() => {
      this.activeSlideIndex = (this.activeSlideIndex + 1) % this.featuredMovies.length;
    }, 3500);
  }

  openMovie(movie: any) {
    this.router.navigate(['/user/book'], { queryParams: { movieId: movie.movieId } });
  }
}
