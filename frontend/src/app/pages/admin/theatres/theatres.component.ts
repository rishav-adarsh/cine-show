import { Component, OnInit } from '@angular/core';
import { TheatreService } from 'src/app/services/theatre.service';
import { SeatService } from 'src/app/services/seat.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-theatres',
  templateUrl: './theatres.component.html',
  styleUrls: ['./theatres.component.css'],
})
export class TheatresComponent implements OnInit {
  theatres: any[] = [];
  filteredTheatres: any[] = [];
  isLoading: boolean = false;
  searchTerm: string = '';

  constructor(
    private theatreService: TheatreService,
    private seatService: SeatService
  ) { }

  ngOnInit(): void {
    this.isLoading = true;
    this.theatreService.fetchTheatres().subscribe(
      (data: any) => {
        console.log('theatres:', data);
        this.theatres = data;
        this.applyFilters();
        this.isLoading = false;
      },
      (err: any) => {
        this.isLoading = false;
        Swal.fire('Error!!', 'Error in loading theatres', 'error');
      }
    )
  }

  applyFilters() {
    const query = this.searchTerm.trim().toLowerCase();
    this.filteredTheatres = this.theatres.filter((theatre) => {
      const theatreName = String(theatre.theatreName || '').toLowerCase();
      const location = String(theatre.location || '').toLowerCase();
      return theatreName.includes(query) || location.includes(query);
    });
  }

  setupSeats(theatre: any) {
    Swal.fire({
      title: `Setup Seats for ${theatre.theatreName}`,
      html: `
        <input id="rows" class="swal2-input" placeholder="Number of Rows" type="number" value="10">
        <input id="cols" class="swal2-input" placeholder="Number of Columns" type="number" value="10">
      `,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Create Seats',
      preConfirm: () => {
        const rows = (document.getElementById('rows') as HTMLInputElement).value;
        const cols = (document.getElementById('cols') as HTMLInputElement).value;
        if (!rows || !cols) {
          Swal.showValidationMessage(`Please enter both rows and columns`);
        }
        return { rows: parseInt(rows), cols: parseInt(cols) };
      }
    }).then((result) => {
      if (result.isConfirmed) {
        this.seatService.setupSeats(theatre.theatreId, result.value).subscribe(
          () => {
            Swal.fire('Success!', 'Seats created successfully!', 'success');
          },
          (err) => {
            Swal.fire('Error!', 'Failed to create seats!', 'error');
          }
        );
      }
    });
  }
}

