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

  submitConfirmationForm(): void {

    let confirmationCode = {

      "confirmationCode": this.userConfirmationForm.value["confirmationCode"]
    };

    this.userConfirmationSvc.confirmUserPut(this.userID, confirmationCode)
      .then(result => {

        alert(`Successfully confirmed user with ID: ${result.userID}, you may now log into your account!`);
        this.router.navigate(['/login']);
      })
      .catch(error => {

        if (error.incorrectCode) {
          alert(`Error: ${error.incorrectCode}`)
        }

        alert(`Error: ${JSON.stringify(error)}`);
      })
    
    this.userConfirmationForm.reset();
  }
}
