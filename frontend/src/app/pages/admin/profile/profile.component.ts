import { Component } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent {
  activeUser: any = null;
  userRole?: string = '';
  backLink: string = '/';

  constructor(private loginService: LoginService) {
    this.activeUser = loginService.getActiveUser();
    this.userRole = loginService.getActiveUserRole();
    this.backLink = this.userRole === 'admin-role-csid' ? '/admin' : '/user';
  }
}
