import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { DEMO_CREDENTIALS } from 'src/app/constants/demo-credentials';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  hide: boolean = true;
  isLoggingIn = false;

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
    if (!this.loginUser.username) {
      this.snackBar.open('Username is Required', 'OK', {
        duration: 3 * 1000,
      });
      return;
    }

    if (!this.loginUser.password) {
      this.snackBar.open('Password is Required', 'OK', {
        duration: 3 * 1000,
      });
      return;
    }

    this.authenticate(this.loginUser);
  }

  onDemoLogin() {
    this.authenticate(DEMO_CREDENTIALS);
  }

  private authenticate(credentials: { username: string; password: string }) {
    if (this.isLoggingIn) {
      return;
    }

    this.isLoggingIn = true;
    this.loginUser = { ...credentials };

    this.loginService.loginWithCredentials(credentials).subscribe({
      next: (user: any) => {
        this.isLoggingIn = false;

        this.router.navigateByUrl('/').then(() => {
          window.location.reload();
        });

        this.snackBar.open(`Welcome ${user.username} !!`, 'OK', {
          duration: 3 * 1000,
        });
      },
      error: () => {
        this.isLoggingIn = false;
        this.snackBar.open('Invalid Credentials!! Try Again..', 'OK', {
          duration: 3 * 1000,
        });
      },
    });
  }
}
