import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { AuthorizationInterceptor } from "./authorization.interceptor";
import { ErrorHandlerInterceptor } from "./error.handler.interceptor";

export const httpInterceptorsProvider=[
  {provide:HTTP_INTERCEPTORS,useClass:AuthorizationInterceptor,multi:true},
  {provide:HTTP_INTERCEPTORS,useClass:ErrorHandlerInterceptor,multi:true}
]
