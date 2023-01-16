import {Component, Input} from '@angular/core';
import {HotelModel} from "../models/hotel.model";

@Component({
  selector: 'app-hotel-item',
  templateUrl: './hotel-item.component.html',
  styleUrls: ['./hotel-item.component.scss']
})
export class HotelItemComponent {
  @Input()
  hotelItem!:HotelModel;


}
