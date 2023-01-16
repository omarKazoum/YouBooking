import {Injectable} from "@angular/core";
import {map, Observable, of} from "rxjs";
import {HotelsService} from "./hotels.service";
import {RoomModel} from "../models/room.model";
import {HotelModel} from "../models/hotel.model";
import {Router, UrlSerializer} from "@angular/router";
import {HttpClient, HttpParams} from "@angular/common/http";
import {API_BASE_URL} from "../app.config";
import {PageModel} from "../models/page.model";
import {tap} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class RoomsService {
  constructor(private hotelsService: HotelsService,private router:Router,private http:HttpClient, private serializer: UrlSerializer) {
  }

  getHotels(): Observable<HotelModel[]> {
    return this.hotelsService.getHotels(0, 0).pipe(map(page=>page.content));
  }

  getRoomById(roomId: number) :Observable<RoomModel>{
    return this.http.get<RoomModel>(`${API_BASE_URL}/rooms/${roomId}`);
  }

  addRoom(roomModel: RoomModel):Observable<RoomModel> {
    return this.http.post<RoomModel>(`${API_BASE_URL}/rooms/`,roomModel);
  }

  goToRoomsHome() {
    this.router.navigateByUrl("/rooms")
  }

  updateRoom(roomModel: RoomModel):Observable<RoomModel> {
    return this.http.put<RoomModel>(`${API_BASE_URL}/rooms/`,roomModel);
  }

  getRooms( params:{hotelId:number, hotelName:string, fromDate:string,toDate:string, cityId :number,page:number,size:number}={page:0,size:6,fromDate:'',hotelName:'',toDate:'',cityId:0,hotelId:0}):Observable<PageModel<RoomModel>> {
    let httpsParams=new HttpParams()
    Object.keys(params).forEach(k=>{
      // @ts-ignore
      if(params[k])
        { // @ts-ignore
          httpsParams=httpsParams.set(k,params[k]);
        }
    })
    return this.http.get<PageModel<RoomModel>>(`${API_BASE_URL}/rooms/?${httpsParams.toString()}`).pipe(tap(res=>console.log("response from server",res)));
  }
}
