import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  hide: boolean = true;

  constructor(
    private snackBar: MatSnackBar,
    private loginService: LoginService,
    private router: Router
  ) {}

  loginUser = {
    username: '',
    password: '',
  };

  onLogin() {
    let username = this.loginUser.username;
    if (!username) {
      this.snackBar.open('Username is Required', 'OK', {
        duration: 3 * 1000,
      });
      return;
    }

    let password = this.loginUser.password;
    if (!password) {
      this.snackBar.open('Password is Required', 'OK', {
        duration: 3 * 1000,
      });
      return;
    }

    this.loginService.generateToken(this.loginUser).subscribe(
      (data: any) => {
        console.log('success: ', data);
        this.loginService.setJwtToken(data.token);
        // this.loginService.loginStatusSubject.next(true);

        this.loginService.fetchActiveUser().subscribe(
          (user: any) => {
            this.loginService.setActiveUser(user);
            console.log('user: ', user);
            if (this.loginService.getActiveUserRole() === 'ADMIN') {
              this.router.navigateByUrl('/admin');
              this.snackBar.open('Welcome BOSS !!', 'OK', {
                duration: 3 * 1000,
              });
              // or window.location.href = "/admin"
            } else if (this.loginService.getActiveUserRole() === 'NORMAL') {
              this.router.navigateByUrl('/user');
              this.snackBar.open(`Welcome ${user.username} !!`, 'OK', {
                duration: 3 * 1000,
              });
            } else {
              this.loginService.logout();
              this.snackBar.open('User role not found!!', 'OK', {
                duration: 3 * 1000,
              });
            }
          },
          (err: any) => {
            console.log('error: ', err);
            this.snackBar.open('Invalid Credentials!!', 'OK', {
              duration: 3 * 1000,
            });
          }
        );
      },
      (err: any) => {
        console.log('error: ', err);
        this.snackBar.open('Invalid Credentials!! Try Again..', 'OK', {
          duration: 3 * 1000,
        });
      }
    );
  }
}
