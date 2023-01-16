import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {HotelModel} from "../models/hotel.model";
import {ActivatedRoute, UrlSegment} from "@angular/router";
import {AuthenticationService} from "../services/authentication.service";
import {RoomModel} from "../models/room.model";
import {RoomsService} from "../services/rooms.service";

@Component({
  selector: 'app-room-form',
  templateUrl: './room-form.component.html',
  styleUrls: ['./room-form.component.scss']
})
export class RoomFormComponent implements OnInit{
  roomFormGroup!:FormGroup;
  formMode!:'edit'|'add';
  roomIdToUpdate!:number;
  hotels$!: Observable<HotelModel[]>;
  role!:'ADMIN'|'CLIENT'|'OWNER';
  data:RoomModel=new RoomModel();
  constructor(private activatedRoute:ActivatedRoute,
              private formBuilder:FormBuilder,
              private roomsService:RoomsService,private authService:AuthenticationService){
  }

  ngOnInit(): void {
    this.data=new RoomModel();
    this.role=this.authService.getTokenFromLocalStorage().role;
    this.hotels$=this.roomsService.getHotels();
    this.roomFormGroup=this.formBuilder.group({
      id:[null],
      reference:[null,[Validators.required,Validators.minLength(1)]],
      isAvailable:[false],
      hotelId:[1,[Validators.required,Validators.min(1)]],
      price:[100,[Validators.required,Validators.min(100)]]
    })
    // @ts-ignore
    this.roomFormGroup.valueChanges.subscribe(
      // @ts-ignore
      formValues=> {
        this.data = {...formValues};
      });
    console.log("ng on init room form")
    this.activatedRoute.url.subscribe((urlSegments:UrlSegment[])=>{
      //TODO:: if the url segment is edit we need to load the hotel to edit

      //if the url segment we need to do nothing
      // @ts-ignore
      this.formMode= urlSegments[0] as unknown as string;
      if(this.formMode=='edit') {
        this.roomIdToUpdate = urlSegments[1] as unknown as number;
        this.roomsService.getRoomById(this.roomIdToUpdate).subscribe(roomFromServer=> {
            this.data = roomFromServer;
            this.roomFormGroup.patchValue({...roomFromServer},{emitEvent:true,onlySelf:false});
            console.log("data from server ",roomFromServer)
          }
        )
      }
    });
  }

  onSubmit($event: any) {
    if(this.formMode=='add') {
      this.roomsService.addRoom(this.data).subscribe(() => {
        alert("room added ")
        this.roomsService.goToRoomsHome();
      });
    }
    else{
      this.roomsService.updateRoom(this.data).subscribe(() => {
        alert("room updated successfully ")
        this.roomsService.goToRoomsHome();
      });
    }
  }
}
