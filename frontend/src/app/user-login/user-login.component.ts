import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { MessageService } from 'primeng/api';
import { UserLoginService } from './user-login.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css',
})
  
export class UserLoginComponent implements OnInit {

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private messageSvc = inject(MessageService);
  private userLoginSvc = inject(UserLoginService);
  
  userLoginForm!: FormGroup;

  ngOnInit(): void {

    this.userLoginForm = this.createLoginForm();
  }

  createLoginForm(): FormGroup {
    
    return this.fb.group({

      username: this.fb.control<string>('', [Validators.required]),

      password: this.fb.control<string>('', [Validators.required])
    });
  }

  submitLoginForm(): void {

    const loginDetails = this.userLoginSvc.parseLoginForm(this.userLoginForm);

    this.userLoginSvc.loginUser(loginDetails)
      .then(res => {
        
        this.messageSvc.add({
          severity: 'success',
          summary: 'Success',
          detail: `Logged in with User ID: ${res.userID}`,
          life: 5000,
        });
        this.userLoginForm.reset();
        
        this.router.navigate(['/']);
      })
      .catch(err => {

        const errorMessage = err?.error?.authenticationFailure || "Unknown error occurred.";

        this.messageSvc.add({
          severity: 'error',
          summary: 'Error',
          detail: errorMessage,
          life: 5000,
        });
      });
  }
}
