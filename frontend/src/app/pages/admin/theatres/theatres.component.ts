import { Component, OnInit } from '@angular/core';
import { TheatreService } from 'src/app/services/theatre.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-theatres',
  templateUrl: './theatres.component.html',
  styleUrls: ['./theatres.component.css'],
})
export class TheatresComponent implements OnInit {
  theatres: any = [];

  constructor(private theatreService: TheatreService) { }

  ngOnInit(): void {
    this.theatreService.fetchTheatres().subscribe(
      (data: any) => {
        console.log('theatres:', data);
        this.theatres = data;
      },
      (err: any) => {
        Swal.fire('Error!!', 'Error in loading theatres', 'error');
      }
    )
  }
}
