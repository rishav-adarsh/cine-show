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
        this.loadSeats(data.theatre.theatreId);
      },
      (error: any) => {
        this.errorMessage = 'Failed to load show details. The show might not exist or there was a server error.';
        Swal.fire('Error :(', 'Error in loading show details!!', 'error');
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

    // Convert map to sorted 2D array
    this.seatRows = Object.keys(rows)
      .map(key => Number(key))
      .sort((a, b) => a - b)
      .map(rowNum => rows[rowNum].sort((a, b) => a.col - b.col));
  }

  isSeatOccupied(seatId: string): boolean {
    return this.show && this.show.bookedSeatIds && this.show.bookedSeatIds.includes(seatId);
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

    // 1. Create the booking in MongoDB
    const bookingData = {
      userId: user.csid,
      showId: this.show.csid,
      seatNumbers: this.selectedSeatNumbers,
      totalAmount: this.selectedSeatsCount * this.show.ticketPrice,
      status: 'PENDING',
      paymentDetails: {
        paymentMethod: 'ONLINE',
        paymentStatus: 'PAID',
        transactionId: 'TXN-' + Date.now()
      }
    };

    this.bookingService.createBooking(bookingData).subscribe(
      (booking: any) => {
        // 2. Update the Show with the newly booked seats
        const updatedBookedSeatIds = [
          ...(this.show.bookedSeatIds || []),
          ...Array.from(this.selectedSeatIds)
        ];

        const showData = {
          movieId: this.show.movie.movieId,
          theatreId: this.show.theatre.theatreId,
          startTime: this.show.startTime,
          endTime: this.show.endTime,
          ticketPrice: this.show.ticketPrice,
          bookedSeatIds: updatedBookedSeatIds
        };

        this.showService.bookMySeats(this.show.csid, showData).subscribe(
          (updatedShow: any) => {
            Swal.fire(
              'Success :)',
              `Booking successful for ${this.show.movie.movieName}! Total: Rs. ${booking.totalAmount}`,
              'success'
            );
            this.selectedSeatIds.clear();
            this.selectedSeatNumbers = [];
            this.selectedSeatsCount = 0;
            this.loadShowDetails(); // Refresh to show occupied seats
          },
          (err: any) => {
            Swal.fire('Error :(', 'Show update failed after booking!!', 'error');
          }
        );
      },
      (err: any) => {
        Swal.fire('Error :(', 'Server Error while creating booking!!', 'error');
      }
    );
  }
}

