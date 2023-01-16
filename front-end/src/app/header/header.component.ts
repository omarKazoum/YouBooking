import {Component, OnInit} from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import {AuthModel} from "../models/auth.model";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit{
  role!:string;
  fullName!:string;
  constructor(private authService:AuthenticationService) {
  }
  logoutClicked() {
    this.authService.logout();
  }

  ngOnInit(): void {
    let authData:AuthModel=this.authService.getTokenFromLocalStorage();
    this.role=authData.role;
    this.fullName=authData.firstName+' '+authData.lastName;
  }
}
