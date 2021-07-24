import {
    Component,
    OnInit,
    OnDestroy,
    Renderer2,
    HostBinding
} from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AppService } from '@services/app.service';
import { Router } from '@angular/router';


@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {

    



    @HostBinding('class') class = 'login-box';
    public loginForm: FormGroup;
    public isAuthLoading = false;
    public isGoogleLoading = false;
    public isFacebookLoading = false;

    constructor(
        private renderer: Renderer2,
        private toastr: ToastrService,
        private appService: AppService,
        private router: Router
    ) { }

    ngOnInit() {
        this.renderer.addClass(
            document.querySelector('app-root'),
            'login-page'
        );
        this.loginForm = new FormGroup({
            username: new FormControl(null, Validators.required),
            password: new FormControl(null, Validators.required)
        });
    }

    async loginByAuth() {
        if (this.loginForm.valid) {
            this.isAuthLoading = true;
            await this.appService.loginByAuth(this.loginForm.value);
            this.isAuthLoading = false;
        } else {
            this.toastr.error('Form is not valid!');
        }
    }

    async loginByGoogle() {
        this.isGoogleLoading = true;
        await this.appService.loginByGoogle();
        this.isGoogleLoading = false;
    }

    async loginByFacebook() {
        this.isFacebookLoading = true;
        await this.appService.loginByFacebook();
        this.isFacebookLoading = false;
    }

    ngOnDestroy() {
        this.renderer.removeClass(
            document.querySelector('app-root'),
            'login-page'
        );
    }

    //my code 
    loginByUserAuth() {
        if (this.loginForm.valid) {
            this.isAuthLoading = true;
            this.appService.generateToken(this.loginForm.value).subscribe((data: any) => {
                //login...
                this.appService.loginUser(data.token);
                this.appService.getCurrentUser().subscribe((user: any) => {
                    this.appService.setUser(user);
                    console.log(user);

                    //redirect ..ADMIN: admin-dashboard
                    //redirect ..NORMAL normal-user
                    if (this.appService.getUserRole() == "ROLE_USER") {
                        //user dashboard
                        // window.location.href="/user-dashboard";
                        this.isAuthLoading = false;
                        this.router.navigate(['user-dashboard']);
                        this.appService.loginStatusSubject.next(true);


                    } else if ((this.appService.getSuperAdminRole().authority == "ROLE_SUPER_ADMIN") || (this.appService.getAdminRole().authority == "ROLE_ADMIN")) {
                        //admin dashboard
                        // window.location.href="/admin";

                        this.isAuthLoading = false;
                        this.router.navigate(['admin']);
                        this.appService.loginStatusSubject.next(true);

                    } else {
                        this.appService.logout();

                    }
                }
                );

            },
                (error) => {
                    this.isAuthLoading = false;
                    this.toastr.error(error.error.message);

                });
        }
    }

}
