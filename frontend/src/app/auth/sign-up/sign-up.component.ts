import { formatCurrency } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {FormGroup, Validators} from '@angular/forms'
import {FormControl} from '@angular/forms'
import { AuthService } from '../shared/auth.service';
import { SignUpRequestPayload } from './signup-request.payload';
@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit{
  
  signUpForm: FormGroup;
  signupRequestPayload: SignUpRequestPayload;
  constructor(private authService:AuthService){
    this.signupRequestPayload={
      username: '',
      email: '',
      password: ''
    }
  }

  ngOnInit(): void {
    this.signUpForm= new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    })
  }
  signup(){
    this.signupRequestPayload.username=this.signUpForm.get('username').value;
    this.signupRequestPayload.email=this.signUpForm.get('email').value;
    this.signupRequestPayload.password=this.signUpForm.get('password').value;

    this.authService.signup(this.signupRequestPayload)
      .subscribe(data=> {console.log(data);})
  }
  }
