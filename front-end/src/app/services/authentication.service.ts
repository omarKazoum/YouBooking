import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {API_BASE_URL} from '../app.config';
import { RegisterModel } from "../models/Register.model";
@Injectable({providedIn:'root'})
export class AuthenticationService{
  constructor(private httpClient: HttpClient) {
  }
  public register(registerModel:RegisterModel):void{
    //send request to the server and redirect the user if
    //the request was a success else display validation errors
    console.log("created an instance of auth service ",API_BASE_URL)
    this.httpClient.post(API_BASE_URL+'/auth/register',registerModel);
  }

}
