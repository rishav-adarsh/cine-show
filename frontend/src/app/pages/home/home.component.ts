import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { MovieService } from 'src/app/services/movie.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  movies: any[] = [];
  isLoading = true;

  constructor(
    public loginService: LoginService,
    private router: Router,
    private movieService: MovieService
  ) { }

  ngOnInit(): void {
    const role = this.loginService.getActiveUserRole();
    if (role === 'admin-role-csid') {
      this.router.navigateByUrl('/admin');
    } else if (role === 'default-role-csid') {
      this.router.navigateByUrl('/user');
    } else {
      // Not logged in, load content for the home page
      this.loadMovies();
    }
  }

  loadMovies() {
    this.movieService.fetchMovies().subscribe({
      next: (data) => {
        this.movies = data.slice(0, 4); // Show only top 4 on home
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching movies', err);
        this.isLoading = false;
      }
    });
  }
}
