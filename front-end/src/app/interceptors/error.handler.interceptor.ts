import {Injectable} from "@angular/core";
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, Observable, tap} from "rxjs";
import {AuthenticationService} from "../services/authentication.service";
import { ToastrService } from "ngx-toastr";

@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor{
  constructor(private authService:AuthenticationService,private toastrService:ToastrService) {
  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log("intercepted request in errors handler")
    // @ts-ignore
    return next.handle(req).pipe(
      // @ts-ignore
      catchError(err=>this.showError(err))
    );
  }
  public showError(errorResponse:HttpErrorResponse):HttpErrorResponse{
    console.log("toastr service ",this.toastrService)
    this.toastrService.clear();
    this.toastrService.error(errorResponse.error.toString(),'',{timeOut:0})
    return errorResponse;
  }


}
