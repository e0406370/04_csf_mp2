import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserConfirmationService } from './user-confirmation.service';

@Component({
  selector: 'app-user-confirmation',
  templateUrl: './user-confirmation.component.html',
  styleUrl: './user-confirmation.component.css'
})
  
export class UserConfirmationComponent implements OnInit {

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private userConfirmationSvc = inject(UserConfirmationService);

  userConfirmationForm!: FormGroup;
  userID!: string;

  ngOnInit(): void {

    this.userConfirmationForm = this.createConfirmationForm();
    this.userID = this.userConfirmationSvc.userID;
  }

  createConfirmationForm(): FormGroup {

    return this.fb.group({

      confirmationCode: this.fb.control<string>('', [
        Validators.required,
        Validators.maxLength(6),
      ])
    })
  }

}
