import { Component, OnInit, inject, HostListener, ViewChild, ElementRef } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  loginService: LoginService = inject(LoginService);
  activeUser: any = null;

  @ViewChild('accountMenu') accountMenu!: ElementRef<HTMLDetailsElement>;

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    if (this.accountMenu && this.accountMenu.nativeElement.open) {
      if (!this.accountMenu.nativeElement.contains(event.target as Node)) {
        this.accountMenu.nativeElement.open = false;
      }
    }
  }

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
