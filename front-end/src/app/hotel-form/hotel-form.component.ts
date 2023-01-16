import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import {map, Observable} from 'rxjs';
import {CityModel} from '../models/city.model'
import {HotelsService} from "../services/hotels.service";
import {AuthenticationService} from "../services/authentication.service";
import {HotelModel} from "../models/hotel.model";
import {tap} from "rxjs/operators";
@Component({
  selector: 'app-hotel-form',
  templateUrl: './hotel-form.component.html',
  styleUrls: ['./hotel-form.component.scss']
})
export class HotelFormComponent implements OnInit{
  hotelFormGroup!:FormGroup;
  formMode!:'edit'|'add';
  hotelIdToUpdate!:number;
  cities$!: Observable<CityModel[]>;
  role!:'ADMIN'|'CLIENT'|'OWNER';
  data:HotelModel=new HotelModel();
  fileInput!:HTMLElement|null;
  constructor(private activatedRoute:ActivatedRoute,private formBuilder:FormBuilder,private hotelService:HotelsService,private authService:AuthenticationService){
  }

  ngOnInit(): void {
    this.fileInput=document.getElementById("image");
    this.data=new HotelModel();
    this.role=this.authService.getTokenFromLocalStorage().role;
    this.cities$=this.hotelService.getCities();
    this.hotelFormGroup=this.formBuilder.group({
      id:[null],
      title:[null,[Validators.required,Validators.minLength(3)]],
      description:[null,[Validators.required,Validators.minLength(10)]],
      image:[null],
      cityId:[null,[Validators.required,Validators.min(1)]],
      isAvailable:[false],
      isApproved:[false]
    })
    this.hotelFormGroup.valueChanges.subscribe(
      formValues=> {
        let tempImageBase64=this.data.imageBase64;
        this.data = {...formValues};
        if(!this.data.imageBase64)
          this.data.imageBase64=tempImageBase64;
          // @ts-ignore
        delete this.data.image;
        const reader = new FileReader();
        reader.onload = () => {
          console.log(reader.result);
          this.data.imageBase64 = reader.result;
        }
        // @ts-ignore
        if (this.fileInput.files.length > 0) { // @ts-ignore
          reader.readAsDataURL(this.fileInput.files[0]);
          // @ts-ignore
          this.data.imageName = this.fileInput.files?.item(0)?.name || '';
        } else if (this.formMode == 'edit')
        {
          let temp=this.data.imageBase64;
          this.data.imageBase64="";
          this.data.imageBase64=temp
        }
      });
    // @ts-ignore
    this.activatedRoute.url.subscribe(url=>{
      //TODO:: if the url segment is edit we need to load the hotel to edit

      //if the url segment we need to do nothing
      // @ts-ignore
      this.formMode=url[0];
      if(this.formMode=='edit') {
        this.hotelIdToUpdate = url[1] as unknown as number;
      this.hotelService.getHotelById(this.hotelIdToUpdate).subscribe(hotelFromServer=> {
          this.data = hotelFromServer
          this.hotelFormGroup.patchValue({...hotelFromServer},{emitEvent:true,onlySelf:false});
          console.log("data from server ",hotelFromServer)
        }
      )
      }
    });
  }

  onSubmit($event: any) {
    if(this.formMode=='add') {
      this.hotelService.addHotel(this.data).subscribe(() => {
        alert("room added ")
        this.hotelService.goToHotelsHome();
      });
    }
    else{
      this.hotelService.updateHotel(this.data).subscribe(() => {
        alert("room updated successfully ")
        this.hotelService.goToHotelsHome();
      });
    }
  }
}
