import { Component, OnInit, inject } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  // isLoggedIn: boolean = false;
  // activeUser: any = null;
  loginService: LoginService = inject(LoginService);

  ngOnInit(): void {
    // this.loginService.loginStatusObservable.subscribe(
    //   (data) => {
    //     this.isLoggedIn = this.loginService.isLoggedIn();
    //     this.activeUser = this.loginService.getActiveUser();
    //   }
    // )
  }

  logoutUser() {
    this.loginService.logout();
  }
}
