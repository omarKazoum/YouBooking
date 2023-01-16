import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../services/authentication.service";

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.scss']
})
export class RoomsComponent implements OnInit{
  role!:'CLIENT'|'ADMIN'|'OWNER';
  constructor(private authService:AuthenticationService){

  }

  ngOnInit() {
    this.role=this.authService.getTokenFromLocalStorage().role;
  }

}
