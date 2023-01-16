import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {HotelFormComponent} from "./hotel-form/hotel-form.component";
import {HotelsListComponent} from "./hotels-list/hotels-list.component";
import {HotelsComponent} from "./hotels/hotels.component";
import {LoginComponent} from "./login/login.component";
import {MainContentComponent} from "./main-content/main-content.component";
import {RegisterComponent} from "./register/register.component";
import {RoomsListComponent} from "./rooms-list/rooms-list.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {RoomFormComponent} from "./room-form/room-form.component";
import {RoomsComponent} from "./rooms/rooms.component";


const routesList: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {
    path: '', component: MainContentComponent
    , children: [
      {path: '', redirectTo: 'hotels', pathMatch: 'full'},
      {
        path: 'hotels', component: HotelsComponent,
        children: [
          {path: '', component: HotelsListComponent},
          {path: 'add', component: HotelFormComponent},
          {path: 'edit/:hotelId', component: HotelFormComponent}
        ]
      },
      {
        path: 'rooms', component: RoomsComponent, children: [
          {path: '', component: RoomsListComponent},
          {path: 'add', component: RoomFormComponent},
          {path: 'edit/:roomId', component: RoomFormComponent}
        ]
      }
    ]
  },
  {path: '**', component: PageNotFoundComponent},

]

@NgModule({
  imports: [RouterModule.forRoot(routesList)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
