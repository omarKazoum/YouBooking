import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {HotelModel} from "../models/hotel.model";
import {HotelsService} from "../services/hotels.service";
import {PageModel} from "../models/page.model";
import {AuthenticationService} from "../services/authentication.service";

@Component({
  selector: 'app-hotels-list',
  templateUrl: './hotels-list.component.html',
  styleUrls: ['./hotels-list.component.scss']
})
export class HotelsListComponent implements OnInit{
  hotels$!: Observable<PageModel<HotelModel>>;
  role!:'CLIENT'|'ADMIN'|'OWNER';
  constructor(private hotelsService:HotelsService,private authService:AuthenticationService) {
  }
  ngOnInit(): void {
    this.hotels$=this.hotelsService.getHotels(0);
    this.role=this.authService.getTokenFromLocalStorage().role;
  }

  switchPage(page: number) {
    this.hotels$=this.hotelsService.getHotels(page);
  }
}
