import { AuthenticationService } from './../../service/authentication/authentication.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {Router } from '@angular/router';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css',
})
export class SignUpComponent implements OnInit {   
  signup_form = new FormGroup({
    full_name: new FormControl<string>('', [Validators.required, Validators.maxLength(30), ]),
    username: new FormControl<string>('', [Validators.required, Validators.maxLength(30), ]),
    email: new FormControl<string>('', [Validators.required, Validators.email]),
    password: new FormControl<string>('', [Validators.required, Validators.maxLength(30)]),
  });

  constructor(private service: AuthenticationService,private router: Router) { }

  signUpUser(){
    if(this.signup_form.value.username!=null&&this.signup_form.value.full_name!=null&&this.signup_form.value.email!=null&&this.signup_form.value.password!=null)
      this.service.registerAndLogin(this.signup_form.value.username,
        this.signup_form.value.full_name,this.signup_form.value.email,this.signup_form.value.password
      ).then(()=>this.router.navigate(['/table']))
      this.signup_form.reset();
      
  }

  ngOnInit() { }
}
