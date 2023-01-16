import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { HeaderComponent } from './header/header.component';
import { HotelFormComponent } from './hotel-form/hotel-form.component';
import { httpInterceptorsProvider } from './interceptors';
import { ManageHotelsComponent } from './manage-hotels/manage-hotels.component';
import { HotelItemComponent } from './hotel-item/hotel-item.component';
import { HotelsListComponent } from './hotels-list/hotels-list.component';
import { HotelsComponent } from './hotels/hotels.component';
import { RouterModule } from '@angular/router';
import { MainContentComponent } from './main-content/main-content.component';
import { RoomItemComponent } from './room-item/room-item.component';
import { RoomsListComponent } from './rooms-list/rooms-list.component';
import { RoomFormComponent } from './room-form/room-form.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { RoomsComponent } from './rooms/rooms.component';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HeaderComponent,
    HotelFormComponent,
    ManageHotelsComponent,
    HotelItemComponent,
    HotelsListComponent,
    HotelsComponent,
    MainContentComponent,
    RoomItemComponent,
    RoomsListComponent,
    RoomFormComponent,
    PageNotFoundComponent,
    RoomsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [ httpInterceptorsProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
