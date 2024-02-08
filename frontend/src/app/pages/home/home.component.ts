import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  constructor(
    private loginService: LoginService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    if (this.loginService.getActiveUserRole() === 'ADMIN') {
      this.router.navigateByUrl('/admin');
    } else if (this.loginService.getActiveUserRole() === 'NORMAL') {
      this.router.navigateByUrl('/user');
    } else {
      this.loginService.logout();
      this.snackBar.open('User role not found!!', 'OK', {
        duration: 3 * 1000,
      });
    }
  }
}
