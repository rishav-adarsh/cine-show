import { Component, OnInit } from '@angular/core';
import { BookingService } from 'src/app/services/booking.service';
import { LoginService } from 'src/app/services/login.service';
import { ShowService } from 'src/app/services/show.service';
import { SeatService } from 'src/app/services/seat.service';
import { forkJoin, map, switchMap } from 'rxjs';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-my-bookings',
  templateUrl: './my-bookings.component.html',
  styleUrls: ['./my-bookings.component.css']
})
export class MyBookingsComponent implements OnInit {
  bookings: any[] = [];
  isLoading: boolean = true;

  constructor(
    private bookingService: BookingService,
    private loginService: LoginService,
    private showService: ShowService,
    private seatService: SeatService
  ) { }

  ngOnInit(): void {
    this.loadUserBookings();
  }

  loadUserBookings() {
    const user = this.loginService.getActiveUser();
    if (!user) return;

    this.bookingService.getBookingsByUserId(user.csid).subscribe(
      (data: any[]) => {
        if (!data || data.length === 0) {
          this.bookings = [];
          this.isLoading = false;
          return;
        }

        // For each booking, we need to fetch show and seat details
        const enrichedBookings$ = data.map(booking => {
          return this.showService.fetchShowByShowId(booking.showId).pipe(
            switchMap((show: any) => {
              // Fetch both show seats and theatre seats to get seat numbers
              return forkJoin({
                showSeats: this.seatService.getShowSeats(booking.showId),
                theatreSeats: this.seatService.getSeatsByTheatre(show.theatre.theatreId)
              }).pipe(
                map(({ showSeats, theatreSeats }) => {
                  const bookingShowSeats = showSeats.filter((s: any) => s.bookingId === booking.csid);
                  const seatNumbers = bookingShowSeats.map((bss: any) => {
                    const originalSeat = theatreSeats.find((ts: any) => ts.csid === bss.seatId);
                    return originalSeat ? originalSeat.seatNumber : bss.seatId;
                  });

                  return {
                    ...booking,
                    movieName: show.movie.movieName,
                    movieGenre: show.movie.genre,
                    movieDuration: show.movie.duration,
                    movieLanguage: show.movie.language,
                    moviePoster: show.movie.poster,
                    theatreName: show.theatre.theatreName,
                    theatreLocation: show.theatre.location,
                    startTime: show.startTime,
                    endTime: show.endTime,
                    seatNumbers: seatNumbers,
                    status: booking.status,
                    paymentMethod: booking.paymentMethod,
                    transactionId: booking.transactionId
                  };
                })
              );
            })
          );
        });

        forkJoin(enrichedBookings$).subscribe(
          (enrichedData) => {
            this.bookings = enrichedData.sort((a, b) => new Date(b.bookedAt).getTime() - new Date(a.bookedAt).getTime());
            this.isLoading = false;
          },
          (err) => {
            console.error(err);
            this.isLoading = false;
            Swal.fire('Error', 'Failed to load booking details', 'error');
          }
        );
      },
      (error) => {
        console.error(error);
        this.isLoading = false;
        Swal.fire('Error', 'Failed to fetch bookings', 'error');
      }
    );
  }

  getStatusClass(status: string) {
    switch (status) {
      case 'CONFIRMED': return 'status-confirmed';
      case 'PENDING': return 'status-pending';
      case 'CANCELED': return 'status-cancelled';
      default: return '';
    }
  }

  formatDuration(minutes: number): string {
    if (!minutes) return 'N/A';
    const hrs = Math.floor(minutes / 60);
    const mins = minutes % 60;
    return `${hrs}h ${mins}m`;
  }
}
