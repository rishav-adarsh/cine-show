import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';
import { MovieService } from 'src/app/services/movie.service';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {
  activeUser: any;
  movies: any[] = [];
  isLoading = true;

  constructor(
    private loginService: LoginService,
    private movieService: MovieService
  ) {}

  ngOnInit(): void {
    this.activeUser = this.loginService.getActiveUser();
    this.loadMovies();
  }

  loadMovies() {
    this.movieService.fetchMovies().subscribe({
      next: (data) => {
        this.movies = data.slice(0, 3);
        this.isLoading = false;
      },
      error: (err) => {
        console.error(err);
        this.isLoading = false;
      }
    });
  }
}
