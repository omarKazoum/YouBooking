import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {HotelsService} from "../services/hotels.service";
import {AuthenticationService} from "../services/authentication.service";
import {filter, Observable} from "rxjs";
import {PageModel} from "../models/page.model";
import {RoomModel} from "../models/room.model";
import {RoomsService} from "../services/rooms.service";
import {formatDate} from "@angular/common";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CityModel} from "../models/city.model";
import {ActivatedRoute, Router, RouterEvent} from "@angular/router";

@Component({
  selector: 'app-rooms-list',
  templateUrl: './rooms-list.component.html',
  styleUrls: ['./rooms-list.component.scss']
})
export class RoomsListComponent implements OnInit,OnChanges{
  role!:'CLIENT'|'ADMIN'|'OWNER';
  rooms$!: Observable<PageModel<RoomModel>>;
  searchFormGroup!: FormGroup;
  cities$!: Observable<CityModel[]>;
  isFilterApplied:boolean=false;
  currentPage:number=0;
  selectedHotelId:number=0;
  constructor(private authService:AuthenticationService,private roomsService:RoomsService, private formBuilder:FormBuilder,private hotelService:HotelsService,private activatedRoute:ActivatedRoute) {
    this.role=this.authService.getTokenFromLocalStorage().role;
  }
  ngOnInit(): void {
    this.cities$=this.hotelService.getCities();
    let fromDateStr:string=formatDate(new Date(),"yyyy-MM-dd",'EN-US');
    let toDate:Date=new Date();
    toDate.setDate(toDate.getDate()+1);
    let toDateStr=formatDate(toDate,"yyyy-MM-dd",'EN-US');
    //preparing search form
      this.searchFormGroup=this.formBuilder.group({
      hotelName:[''],
      fromDate:[fromDateStr,[Validators.required]],
      toDate:[toDateStr,[Validators.required]],
      cityId:[1,[Validators.required]]
    })
    this.activatedRoute.queryParams.subscribe(params=>{
      this.selectedHotelId=params['hotelId'];
      if(this.selectedHotelId===undefined)
        this.selectedHotelId=0;
      this.rooms$=this.roomsService.getRooms({page:0,cityId:0,toDate:toDateStr,hotelId:this.selectedHotelId,hotelName:'',fromDate:fromDateStr,size:6});
    });
  }

  updateResults(page: number=this.currentPage) {
    this.currentPage=page;

    if(this.isFilterApplied){
      this.rooms$=this.roomsService.getRooms({...this.searchFormGroup.value,page:this.currentPage,hotelId:this.selectedHotelId,size:6});
    }else{
      this.rooms$=this.roomsService.getRooms({page:this.currentPage,cityId:0,toDate:formatDate(new Date(),"yyyy-MM-dd",'EN-US'),hotelId:this.selectedHotelId,hotelName:'',fromDate:formatDate(new Date(),"yyyy-MM-dd",'EN-US'),size:6});
    }
  }
  filterSubmit(){
    this.isFilterApplied=true;
    this.updateResults();
  }

  ngOnChanges(changes: SimpleChanges): void {


  }
}
