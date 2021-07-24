import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Gatekeeper } from 'gatekeeper-client-sdk';
import { HttpClient } from '@angular/common/http';
import baseUrl from './helper';
import { Subject } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AppService {
    public loginStatusSubject = new Subject<boolean>();
    public user: any = null;

    constructor(private router: Router, private toastr: ToastrService, private http: HttpClient) { }

    async loginByAuth({ email, password }) {
        try {
            const token = await Gatekeeper.loginByAuth(email, password);
            localStorage.setItem('token', token);
            await this.getProfile();
            this.router.navigate(['/']);
        } catch (error) {
            this.toastr.error(error.response.data.message);
        }
    }

    async registerByAuth({ email, password }) {
        try {
            const token = await Gatekeeper.registerByAuth(email, password);
            localStorage.setItem('token', token);
            await this.getProfile();
            this.router.navigate(['/']);
        } catch (error) {
            this.toastr.error(error.response.data.message);
        }
    }

    async loginByGoogle() {
        try {
            const token = await Gatekeeper.loginByGoogle();
            localStorage.setItem('token', token);
            await this.getProfile();
            this.router.navigate(['/']);
        } catch (error) {
            this.toastr.error(error.response.data.message);
        }
    }

    async registerByGoogle() {
        try {
            const token = await Gatekeeper.registerByGoogle();
            localStorage.setItem('token', token);
            await this.getProfile();
            this.router.navigate(['/']);
        } catch (error) {
            this.toastr.error(error.response.data.message);
        }
    }

    async loginByFacebook() {
        try {
            const token = await Gatekeeper.loginByFacebook();
            localStorage.setItem('token', token);
            await this.getProfile();
            this.router.navigate(['/']);
        } catch (error) {
            this.toastr.error(error.response.data.message);
        }
    }

    async registerByFacebook() {
        try {
            const token = await Gatekeeper.registerByFacebook();
            localStorage.setItem('token', token);
            await this.getProfile();
            this.router.navigate(['/']);
        } catch (error) {
            this.toastr.error(error.response.data.message);
        }
    }

    async getProfile() {
        try {
            this.user = await Gatekeeper.getProfile();
        } catch (error) {
            this.logout();
            throw error;
        }
    }

    // logout() {
    //     localStorage.removeItem('token');
    //     localStorage.removeItem('gatekeeper_token');
    //     this.user = null;
    //     this.router.navigate(['/login']);
    // }

    // My App service code 


    //current user : which is loogedin
    public getCurrentUser() {
        return this.http.get(`${baseUrl}/currentUser`);
    }

    //generate Token
    public generateToken(loginData: any) {
        return this.http.post(`${baseUrl}/generateToken`, loginData);
    }

    //login user : set token in local storage
    public loginUser(token) {
        localStorage.setItem('token', token);
        // this.loginStatusSubject.next(true);
        return true;
    }

    //isLoogedIn: user is loggedin or not
    public isLoggedIn() {
        let tokenStr = localStorage.getItem('token');
        if (tokenStr == undefined || tokenStr == '' || tokenStr == null) {
            return false;
        } else {
            return true;
        }
    }

    //logout:: remove token from localstorage
    public logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        return true;
    }

    //getToken
    public getToken() {
        return localStorage.getItem('token');
    }

    //set userDetail
    public setUser(user) {
        localStorage.setItem('user', JSON.stringify(user));
    }

    //getUser
    public getUser() {
        let userStr = localStorage.getItem('user');
        if (userStr != null) {
            return JSON.parse(userStr);
        } else {
            this.logout();
            return null;
        }
    }

    //get superadmin role
    public getSuperAdminRole() {
        let superAdmin = this.getUser();

        function findSuperAdmin(roleSuperAdmin) {
            return roleSuperAdmin.authority === 'ROLE_SUPER_ADMIN';
        }
        return superAdmin.authorities.find(findSuperAdmin);
    }

    // get admin role
    public getAdminRole() {
        let admin = this.getUser();

        function findAdmin(roleAdmin) {
            return roleAdmin.authority === 'ROLE_ADMIN';
        }
        // console.warn(admin.authorities.find(findAdmin).authority);
        return admin.authorities.find(findAdmin);
    }

    //get user role
    public getUserRole() {
        let user = this.getUser();
        return user.authorities[0].authority;
    }
    //End of my code

}
