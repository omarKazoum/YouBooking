import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-hotels',
  templateUrl: './hotels.component.html',
  styleUrls: ['./hotels.component.scss']
})
export class HotelsComponent implements OnInit{
  role!:'CLIENT'|'ADMIN'|'OWNER';
  constructor(private authService:AuthenticationService){

  }

  ngOnInit() {
    this.role=this.authService.getTokenFromLocalStorage().role;
  }

}
