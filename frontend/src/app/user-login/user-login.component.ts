import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { ERROR_MESSAGE } from '../utility/constants';
import { UtilityService } from '../utility/utility.service';
import { UserLoginService } from './user-login.service';
import { SessionStore } from '../utility/session.store';
import { SessionState } from '../models/sessionstate';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css',
})
  
export class UserLoginComponent implements OnInit {

  private fb = inject(FormBuilder);
  private router = inject(Router);

  private utilitySvc = inject(UtilityService);
  private userLoginSvc = inject(UserLoginService);
  private sessionStore = inject(SessionStore);

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
      .then((res) => {

        this.utilitySvc.generateSuccessMessage(res.message);
        this.userLoginForm.reset();

        this.sessionStore.updateSessionState(res.response as SessionState);

        this.router.navigate(['/']);
      })
      .catch((err) => {

        this.utilitySvc.generateErrorMessage(err?.error?.message || ERROR_MESSAGE);
      });
  }
}
