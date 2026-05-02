import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { BookingService } from 'src/app/services/booking.service';
import { LoginService } from 'src/app/services/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit, OnDestroy {
  bookingDetails: any = null;
  timeLeft: number = 600; // 10 minutes in seconds
  timerInterval: any;
  selectedPaymentMethod: string = 'card';

  constructor(
    private router: Router,
    private bookingService: BookingService,
    private loginService: LoginService,
    private snack: MatSnackBar
  ) { }

  ngOnInit(): void {
    // history.state is more reliable for persistent data during session
    const state = history.state;
    if (state && state.bookingData) {
      this.bookingDetails = state.bookingData;
      this.startTimer();
      this.showLockToast();
    } else {
      console.error('No booking data found in state');
      this.router.navigate(['/user']);
    }
  }

  showLockToast() {
    this.snack.open('Seats are locked for 10 minutes! Complete your payment quickly.', 'OK', {
      duration: 5000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: ['lock-snack']
    });
  }

  ngOnDestroy(): void {
    if (this.timerInterval) {
      clearInterval(this.timerInterval);
    }
  }

  startTimer() {
    this.timerInterval = setInterval(() => {
      if (this.timeLeft > 0) {
        this.timeLeft--;
      } else {
        this.handleTimeout();
      }
    }, 1000);
  }

  get formattedTime() {
    const minutes = Math.floor(this.timeLeft / 60);
    const seconds = this.timeLeft % 60;
    return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
  }

  handleTimeout() {
    clearInterval(this.timerInterval);
    Swal.fire({
      title: 'Session Expired',
      text: 'Your seat lock has expired. Please select seats again.',
      icon: 'warning',
      confirmButtonText: 'Go Back'
    }).then(() => {
      this.router.navigate(['/user-dashboard']);
    });
  }

  confirmPayment() {
    if (!this.bookingDetails) return;

    Swal.fire({
      title: 'Processing Payment...',
      didOpen: () => {
        Swal.showLoading();
      },
      allowOutsideClick: false
    });

    // Map UI selection to Backend Enum
    let backendPaymentMethod = 'CARD';
    switch (this.selectedPaymentMethod) {
      case 'upi': backendPaymentMethod = 'UPI'; break;
      case 'netbanking': backendPaymentMethod = 'NET_BANKING'; break;
      case 'wallet': backendPaymentMethod = 'DIGITAL_WALLET'; break;
      default: backendPaymentMethod = 'CARD';
    }

    // Simulate payment and then finalize booking
    setTimeout(() => {
      const finalBookingData = {
        userId: this.bookingDetails.userId,
        showId: this.bookingDetails.showId,
        seatIds: this.bookingDetails.seatIds,
        totalAmount: this.bookingDetails.totalAmount,
        paymentMethod: backendPaymentMethod,
        transactionId: 'TXN-' + Date.now()
      };

      console.log('Sending final booking data:', finalBookingData);

      this.bookingService.createBooking(finalBookingData).subscribe(
        (res: any) => {
          Swal.close();
          Swal.fire('Success!', 'Tickets booked successfully!', 'success').then(() => {
            this.router.navigate(['/user/my-bookings']);
          });
        },
        (err: any) => {
          Swal.close();
          Swal.fire('Error', 'Booking failed. Please try again.', 'error');
        }
      );
    }, 2000);
  }

  cancelCheckout() {
    this.router.navigate(['/user/seats'], { queryParams: { showId: this.bookingDetails.showId } });
  }
}
