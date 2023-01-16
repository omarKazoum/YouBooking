import {Component, Input} from '@angular/core';
import {RoomModel} from "../models/room.model";

@Component({
  selector: 'app-room-item',
  templateUrl: './room-item.component.html',
  styleUrls: ['./room-item.component.scss']
})
export class RoomItemComponent {
  @Input()
  roomItem!:RoomModel;
  @Input()
  role!:'CLIENT'|'ADMIN'|'OWNER';


}
