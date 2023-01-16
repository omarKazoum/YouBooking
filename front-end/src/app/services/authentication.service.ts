import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import {API_BASE_URL} from '../app.config';
import { AuthModel } from "../models/auth.model";
import { Router } from "@angular/router";
import {catchError, tap } from "rxjs/operators";
@Injectable({providedIn:'root'})
export class AuthenticationService{
  LOGIN_DATA_KEY:string="authToken";
  constructor(private httpClient: HttpClient,private router:Router
              ) {
  }
  public register(registerModel:AuthModel):void{
    this.httpClient.post<AuthModel>(API_BASE_URL+'/auth/register',registerModel).
    subscribe((response)=>{
      this.saveAuthModelToLocalStorage(response as AuthModel);
      this.redirectHome();
    });
  }
  public login(loginData:AuthModel){
    console.log("login called with data ",loginData)
    //TODO implement this
    this.httpClient.post<AuthModel>(API_BASE_URL+'/auth/login',loginData).
    subscribe((response)=>{
      this.saveAuthModelToLocalStorage(response as AuthModel);
      this.redirectHome();
    });
  }
  public redirectToLoginIfNotConnected(){
    const routeStr=this.router.url;
    const isRouteInAllowed=this.router.url in ['/login','/register'];
    console.log("active route="+routeStr+ ' isRouteAllowed='+isRouteInAllowed);
    const tokenFromLocalStorage=this.getTokenFromLocalStorage();
    console.log("token from localstorage =",tokenFromLocalStorage)
    if(!(isRouteInAllowed) && !tokenFromLocalStorage)
    {
      console.log("")
      this.redirectToLogin();
    }
  }
  public redirectToLogin() {
    this.router.navigateByUrl("login");
    }
  public getTokenFromLocalStorage():AuthModel{
    return JSON.parse(localStorage.getItem(this.LOGIN_DATA_KEY) ||'') as AuthModel;
  }
  public saveAuthModelToLocalStorage(authModel:AuthModel){
    localStorage.setItem(this.LOGIN_DATA_KEY,JSON.stringify(authModel) as string);
  }
  public logout(){
    localStorage.clear();
    this.redirectToLogin();
  }
  public redirectHome(){
    this.router.navigateByUrl("hotels");

  }



}
