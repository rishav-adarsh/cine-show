import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ShowService } from 'src/app/services/show.service';
import { SeatService } from 'src/app/services/seat.service';
import { BookingService } from 'src/app/services/booking.service';
import { LoginService } from 'src/app/services/login.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-book-seats',
  templateUrl: './book-seats.component.html',
  styleUrls: ['./book-seats.component.css']
})
export class BookSeatsComponent implements OnInit {

  show: any = null;
  showId: any = null;
  showSeats: any[] = [];
  seatRows: any[][] = [];
  selectedSeatIds: Set<string> = new Set();
  selectedSeatNumbers: string[] = [];
  selectedSeatsCount: number = 0;
  errorMessage: string | null = null;

  constructor(
    private showService: ShowService, 
    private seatService: SeatService,
    private bookingService: BookingService,
    private loginService: LoginService,
    private activeRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.activeRoute.queryParamMap.subscribe((par) => {
      if(par.has('showId')) {
        this.showId = par.get('showId');
        this.loadShowDetails();
      }
    });
  }

  loadShowDetails() {
    this.errorMessage = null;
    this.showService.fetchShowByShowId(this.showId).subscribe(
      (data: any) => {
        this.show = data;
        this.loadShowSeats();
      },
      (error: any) => {
        this.errorMessage = 'Failed to load show details.';
        Swal.fire('Error :(', 'Error in loading show details!!', 'error');
      }
    );
  }

  loadShowSeats() {
    this.seatService.getShowSeats(this.showId).subscribe(
      (showSeats: any[]) => {
        this.showSeats = showSeats;
        this.loadSeats(this.show.theatre.theatreId);
      },
      (error: any) => {
        this.errorMessage = 'Failed to load availability for this show.';
      }
    );
  }

  loadSeats(theatreId: string) {
    this.seatService.getSeatsByTheatre(theatreId).subscribe(
      (seats: any) => {
        this.organizeSeatsIntoRows(seats);
      },
      (error: any) => {
        this.errorMessage = 'Failed to load the seating map for this theatre.';
        Swal.fire('Error :(', 'Error in loading theatre seats!!', 'error');
      }
    );
  }

  organizeSeatsIntoRows(seats: any[]) {
    const rows: { [key: number]: any[] } = {};
    seats.forEach(seat => {
      if (!rows[seat.row]) {
        rows[seat.row] = [];
      }
      rows[seat.row].push(seat);
    });

    this.seatRows = Object.keys(rows)
      .map(key => Number(key))
      .sort((a, b) => a - b)
      .map(rowNum => rows[rowNum].sort((a, b) => a.col - b.col));
  }

  getShowSeat(seatId: string) {
    return this.showSeats.find(ss => ss.seatId === seatId);
  }

  isSeatOccupied(seatId: string): boolean {
    const ss = this.getShowSeat(seatId);
    return ss && (ss.status === 'BOOKED' || ss.status === 'LOCKED');
  }

  isSeatSelected(seatId: string): boolean {
    return this.selectedSeatIds.has(seatId);
  }

  selectSeat(seat: any) {
    if (this.isSeatOccupied(seat.csid)) return;

    if (this.selectedSeatIds.has(seat.csid)) {
      this.selectedSeatIds.delete(seat.csid);
      this.selectedSeatNumbers = this.selectedSeatNumbers.filter(num => num !== seat.seatNumber);
      this.selectedSeatsCount--;
    } else {
      this.selectedSeatIds.add(seat.csid);
      this.selectedSeatNumbers.push(seat.seatNumber);
      this.selectedSeatsCount++;
    }
  }

  bookMySeats() {
    if (this.selectedSeatIds.size === 0) {
      Swal.fire('Info', 'Please select at least one seat!', 'info');
      return;
    }

    const user = this.loginService.getActiveUser();
    if (!user) {
      Swal.fire('Info', 'Please login to book tickets!', 'info');
      this.router.navigate(['/login']);
      return;
    }

    // 1. Lock the seats first
    const lockRequest = {
      seatIds: Array.from(this.selectedSeatIds),
      userId: user.csid
    };

    this.seatService.lockSeats(this.showId, lockRequest).subscribe(
      () => {
        // 2. If locking is successful, navigate to Checkout page
        const bookingData = {
          userId: user.csid,
          showId: this.show.csid,
          movieName: this.show.movie.movieName,
          theatreName: this.show.theatre.theatreName,
          seatIds: Array.from(this.selectedSeatIds),
          seatNumbers: this.selectedSeatNumbers,
          totalAmount: this.selectedSeatsCount * this.show.ticketPrice
        };

        this.router.navigate(['/user/checkout'], { state: { bookingData } });
      },
      (err: any) => {
        Swal.fire('Error :(', 'Some seats were already taken or locked. Please refresh.', 'error');
        this.loadShowDetails();
      }
    );
  }
}

