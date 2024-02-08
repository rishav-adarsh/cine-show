import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ShowService } from 'src/app/services/show.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-book-seats',
  templateUrl: './book-seats.component.html',
  styleUrls: ['./book-seats.component.css']
})
export class BookSeatsComponent implements OnInit {

  show: any = null;
  showId: any = null;
  isSeatBooked: any = [];
  selectedSeatsCount: number = 0;

  constructor(private showService: ShowService, private activeRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.activeRoute.queryParamMap.subscribe((par) => {
      if(par.has('showId')) {
        this.showId = par.get('showId');
      }
    });
    this.showService.fetchShowByShowId(this.showId).subscribe(
      (data: any) => {
        this.show = data;
        this.show.isSeatBooked[0][0] = true;
        for(let row of this.show.isSeatBooked) {
          let cloneRow = []
          for(let seat of row) {
            cloneRow.push(seat);
          }
          this.isSeatBooked.push(cloneRow);
        }
        console.log(data);
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

  selectSeat(row: any, col: any) {
    if(this.show.isSeatBooked[row][col]) return;

    this.isSeatBooked[row][col] = !this.isSeatBooked[row][col];
    if(this.isSeatBooked[row][col]) {
      this.selectedSeatsCount++;
    } else {
      this.selectedSeatsCount--;
    }
  }

  bookMySeats() {
    this.show.isSeatBooked = this.isSeatBooked;
    this.showService.bookMySeats(this.show).subscribe(
      (data: any) => {
        Swal.fire(
          'Success :)',
          `Booking successful of ${this.show.movie.movieName} for Rs. ${this.selectedSeatsCount * this.show.ticketPrice}!!`,
          'success'
        );
        this.selectedSeatsCount = 0;
      },
      (err: any) => {
        Swal.fire('Error :(', 'Server Error !!', 'error');
      }
    );
  }

}
