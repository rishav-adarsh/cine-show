import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { DEMO_CREDENTIALS } from 'src/app/constants/demo-credentials';
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
  isDemoLoggingIn = false;

  constructor(
    public loginService: LoginService,
    private router: Router,
    private movieService: MovieService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const role = this.loginService.getActiveUserRole();
    if (role === 'admin-role-csid') {
      this.router.navigateByUrl('/admin');
    } else if (role === 'default-role-csid') {
      this.router.navigateByUrl('/user');
    } else {
      this.loadMovies();
    }
  }

  onDemoLogin() {
    if (this.isDemoLoggingIn) {
      return;
    }

    this.isDemoLoggingIn = true;

    this.loginService.loginWithCredentials(DEMO_CREDENTIALS).subscribe({
      next: (user: any) => {
        this.isDemoLoggingIn = false;

        this.router.navigateByUrl('/').then(() => {
          window.location.reload();
        });

        this.snackBar.open(`Welcome ${user.username}! Explore the app.`, 'OK', {
          duration: 3 * 1000,
        });
      },
      error: () => {
        this.isDemoLoggingIn = false;
        this.snackBar.open('Demo login failed. Please try again.', 'OK', {
          duration: 3 * 1000,
        });
      },
    });
  }

  loadMovies() {
    this.movieService.fetchMovies().subscribe({
      next: (data) => {
        this.movies = data.slice(0, 4);
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching movies', err);
        this.isLoading = false;
      },
    });
  }
}
