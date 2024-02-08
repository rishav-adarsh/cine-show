import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TheatreService } from 'src/app/services/theatre.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-theatre',
  templateUrl: './add-theatre.component.html',
  styleUrls: ['./add-theatre.component.css']
})
export class AddTheatreComponent {

  theatre: any = {
    theatreName: '',
    location: ''
  }

  constructor(private theatreService: TheatreService, private snackBar: MatSnackBar) { }

  onSubmit() {
    if (!this.theatre.theatreName || !this.theatre.location) {
      this.snackBar.open('All fields are Required!!', 'OK', {
        duration: 3000,
      });
      return;
    }

    this.theatreService.addTheatre(this.theatre).subscribe(
      (data: any) => {
        this.theatre.theatreName = this.theatre.location = '';
        Swal.fire('Success :)', 'Theatre added successfully!!', 'success');
      },
      (err: any) => {
        Swal.fire('Error :(', 'Server Error !!', 'error');
      }
    );
  }

}
