import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { AppService } from '@services/app.service';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate {

  constructor(private login:AppService,private router:Router){

  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if(this.login.isLoggedIn() && this.login.getUserRole()=='ROLE_USER' ){
        return true;
      }
      
    this.router.navigate(['login']);

    return false;
  }
  
}
