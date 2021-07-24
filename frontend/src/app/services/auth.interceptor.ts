import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AppService } from "./app.service";




@Injectable()
export class AuthInterceptor implements HttpInterceptor{

    constructor(private login:AppService){

    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      
        //add the jwt token (LocalStorage) request
        let authReq=req;
        const token=this.login.getToken();
        console.log('Inside Interceptor');
        if(token !=null){
            authReq=authReq.clone({
                setHeaders:{Authorization:`Bearer ${token}`},
            });
        }
        return next.handle(authReq); 
    }
    
}

export const authInterceptorProviders=[
    {
        provide: HTTP_INTERCEPTORS,
        useClass: AuthInterceptor,
        multi: true
    }
];