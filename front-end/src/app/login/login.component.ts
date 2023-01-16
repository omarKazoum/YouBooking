import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{
  loginFormGroup!: FormGroup;
  constructor(private formBuilder:FormBuilder,private authService:AuthenticationService) {
  }
  ngOnInit(): void {
    this.loginFormGroup=this.formBuilder.group({
      email:[null,[Validators.required,Validators.email]],
      password:[null,[Validators.required,Validators.minLength(8)]]
    });
  }
  onSubmitLoginForm(){
    this.authService.login({...this.loginFormGroup.value})
  }

}
