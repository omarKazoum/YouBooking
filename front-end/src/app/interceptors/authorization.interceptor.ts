import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import { Injectable } from "@angular/core";
import {Observable} from "rxjs";
import { AuthenticationService } from "../services/authentication.service";
@Injectable()
export class AuthorizationInterceptor implements HttpInterceptor {
  constructor(private authService:AuthenticationService){

  }
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      let modifiedReq:HttpRequest<any>;
      if(!req.url.toString().includes("auth")){
        let headers=new HttpHeaders().append('Authorization','Bearer '+this.authService.getTokenFromLocalStorage().jwtToken)
        modifiedReq=req.clone({headers});
      }else {
        modifiedReq=req;
      }
      return next.handle(modifiedReq);
    }

}
