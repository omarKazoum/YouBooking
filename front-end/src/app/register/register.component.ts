import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { AuthModel } from '../models/auth.model';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent implements OnInit{
   nameRegex:RegExp=/\w{3,}/;
   passwordRegex:RegExp=/.{8,}/;
   roleRegex:RegExp=/^(CLIENT)|(OWNER)$/;

   registerForm!: FormGroup;

   FIRST_NAME_KEY:string='firstName';
   LAST_NAME_KEY:string='lastName';
   EMAIL_KEY:string='email';
   PASSWORD_KEY:string='password';
   ROLE_KEY:string='role';
   roles:string[]=['CLIENT','OWNER'];

    formPreview$!:Observable<AuthModel>;

  submitted=false;
  constructor(private authService:AuthenticationService,private formBuilder:FormBuilder) {

  }

  ngOnInit(): void {
      let inputs:any=new Object();
      inputs[this.FIRST_NAME_KEY]=['',[Validators.required,Validators.pattern(this.nameRegex)]];
      inputs[this.LAST_NAME_KEY]=['',[Validators.required,Validators.pattern(this.nameRegex)]];
      inputs[this.EMAIL_KEY]=['',[Validators.required,Validators.email]];
      inputs[this.PASSWORD_KEY]=['',[Validators.required,Validators.pattern(this.passwordRegex)]];
      inputs[this.ROLE_KEY]=['',[Validators.required,Validators.pattern(this.roleRegex)]];
      this.registerForm=this.formBuilder.group(inputs)

      //this.formPreview$=this.registerForm.valueChanges;
    }

  onSubmit() {
    console.log("form was submitted");
    this.authService.register({...this.registerForm.value});
  }
}
