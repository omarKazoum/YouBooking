import { Component } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent {
   FIRST_NAME_KEY:string='first-name';
   LAST_NAME_KEY:string='last-name';
   EMAIL_KEY:string='email';
   PASSWORD_KEY:string='password';
   ROLE_KEY:string='role';

  constructor(private authService:AuthenticationService) {
    this.authService.register({password:'',
      lastName:'',
      firstName:'',
      email:'',
      role:'CLIENT'});
  }

}
