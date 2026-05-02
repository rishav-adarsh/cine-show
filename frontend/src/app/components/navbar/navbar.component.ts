import { Component, OnInit, inject } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  loginService: LoginService = inject(LoginService);
  activeUser: any = null;

  ngOnInit(): void {
    this.loginService.loginStatusObservable.subscribe((loggedIn) => {
      if (loggedIn) {
        this.activeUser = this.loginService.getActiveUser();
        if (!this.activeUser) {
          this.loginService.fetchActiveUser().subscribe((user: any) => {
            this.activeUser = user;
            this.loginService.setActiveUser(user);
          });
        }
      } else {
        this.activeUser = null;
      }
    });
  }

  logoutUser() {
    this.loginService.logout();
  }
}
