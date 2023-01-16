import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {API_BASE_URL} from "../app.config";
import {CityModel} from "../models/city.model";
import {Observable} from "rxjs";
import {HotelModel} from "../models/hotel.model";
import * as http from "http";
import {Router} from "@angular/router";
import {tap} from "rxjs/operators";
import {PageModel} from "../models/page.model";

@Injectable({
  providedIn: 'root'
})
export class HotelsService {
  constructor(private http:HttpClient,private router:Router) { }
  public getCities():Observable<CityModel[]>{
    return this.http.get<CityModel[]>(API_BASE_URL+'/selections/cities');
  }
  public addHotel(hotel:HotelModel):Observable<HotelModel>{
    console.log("adding hotel request",hotel);
    return this.http.post<HotelModel>(API_BASE_URL+'/hotels/',hotel);
  }

  public getHotelById(hotelId: number):Observable<HotelModel> {
    return this.http.get<HotelModel>(API_BASE_URL+'/hotels/'+hotelId)
  }

  updateHotel(hotel: HotelModel):Observable<HotelModel> {
    return this.http.put<HotelModel>(API_BASE_URL+'/hotels/',hotel)
  }
  goToHotelsHome(){
    this.router.navigateByUrl("/hotels")
  }

  getHotels(page:number,size:number=6):Observable<PageModel<HotelModel>>{
    return this.http.get<PageModel<HotelModel>>(API_BASE_URL+`/hotels/?page=${page}&size=${size}`);
  }
}
