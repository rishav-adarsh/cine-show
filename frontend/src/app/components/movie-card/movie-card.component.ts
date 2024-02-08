import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-movie-card',
  templateUrl: './movie-card.component.html',
  styleUrls: ['./movie-card.component.css']
})
export class MovieCardComponent {

  @Input() movieData: any;

  constructor(private router: Router) { }

  bookingNavigation() {
    this.router.navigate(
      ['book'],
      {
        queryParams: { movieId: this.movieData.movieId }
      }
    )
  }

}
