import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { PASSWORD_REGEX } from '../utility/constants';
import { UserRegistrationService } from './user-registration.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrl: './user-registration.component.css'
})
  
export class UserRegistrationComponent implements OnInit {

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private userRegistrationSvc = inject(UserRegistrationService);

  userRegistrationForm!: FormGroup;

  ngOnInit(): void {

    this.userRegistrationForm = this.createRegistrationForm();
  }

  createRegistrationForm(): FormGroup {

    return this.fb.group({

      name: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(25),
      ]),

      email: this.fb.control<string>('', [
        Validators.required,
        Validators.email,
        Validators.maxLength(254),
      ]),

      birthDate: this.fb.control<Date>(new Date(), [
        Validators.required,
        this.userRegistrationSvc.pastDateValidator.bind(this),
      ]),

      username: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(7),
        Validators.maxLength(20),
      ]),

      password: this.fb.control<string>('', [
        Validators.required,
        Validators.pattern(PASSWORD_REGEX)
      ])
    });
  }

  submitRegistrationForm(): void {

    const newUser = this.userRegistrationSvc.parseRegistrationForm(this.userRegistrationForm);

    this.userRegistrationSvc.registerUser(newUser)
      .then(result => {
        alert(`User ID: ${result.userID}`);
        this.router.navigate(['/']);
      })
      .catch(error => {
        alert(`Error: ${JSON.stringify(error)}`)
      });
  }
}
