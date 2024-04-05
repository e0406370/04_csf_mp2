import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { UserLoginService } from './user-login.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css',
})
  
export class UserLoginComponent implements OnInit {

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private userLoginSvc = inject(UserLoginService);

  userLoginForm!: FormGroup;

  ngOnInit(): void {

    this.userLoginForm = this.createLoginForm();
  }

  createLoginForm(): FormGroup {
    
    return this.fb.group({

      username: this.fb.control<string>('', [Validators.required]),

      password: this.fb.control<string>('', [Validators.required]),
    });
  }

  submitLoginForm(): void {

    const loginDetails = this.userLoginSvc.parseLoginForm(this.userLoginForm);

    this.userLoginSvc.loginUser(loginDetails)
      .then(result => {
          
        alert(`User ID: ${result.userID}`);
        this.userLoginForm.reset();
        
        this.router.navigate(['/register']);
      })
      .catch(error => {

        if (error.authenticationFailure) {
          alert(`Error: ${error.authenticationFailure}`);
        }

        alert(`Error: ${JSON.stringify(error)}`);
      });
  }
}
