import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {
  activeUser: any;

  constructor(private loginService: LoginService) {}

  ngOnInit(): void {
    this.activeUser = this.loginService.getActiveUser();
  }
}
