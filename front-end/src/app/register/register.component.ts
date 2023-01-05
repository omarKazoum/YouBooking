import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent implements OnInit{

   FIRST_NAME_KEY:string='first-name';
   LAST_NAME_KEY:string='last-name';
   EMAIL_KEY:string='email';
   PASSWORD_KEY:string='password';
   ROLE_KEY:string='role';
   roles:string[]=['CLIENT','OWNER'];

    formData={
      password:'',
      lastName:'',
      firstName:'',
      email:'',
      role:'CLIENT'};
  submitted=false;
  constructor(private authService:AuthenticationService) {
    this.authService.register({password:'',
      lastName:'some last name',
      firstName:'some first name',
      email:'some@email.extension',
      role:'CLIENT'});
  }

  ngOnInit(): void {
      this.formData.email="ezeze";
    }

  onSubmit() {
    this.submitted=true;
    console.log("form was submitted");
  }
}
