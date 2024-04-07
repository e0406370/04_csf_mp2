import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { UserConfirmationService } from './user-confirmation.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-user-confirmation',
  templateUrl: './user-confirmation.component.html',
  styleUrl: './user-confirmation.component.css'
})
  
export class UserConfirmationComponent implements OnInit {

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private messageSvc = inject(MessageService);
  private userConfirmationSvc = inject(UserConfirmationService);

  userConfirmationForm!: FormGroup;
  userID!: string;
  cannotSubmit: boolean = true;

  ngOnInit(): void {

    this.userConfirmationForm = this.createConfirmationForm();
    this.userID = this.userConfirmationSvc.userID;
  }

  onCodeChange(code: string): void {

    this.cannotSubmit = code.length != 6;
    
    this.userConfirmationForm.value["confirmationCode"] = code;
    console.info(this.userConfirmationForm.value["confirmationCode"]);
  }

  createConfirmationForm(): FormGroup {

    return this.fb.group({

      confirmationCode: this.fb.control<string>('')
    });
  }

  submitConfirmationForm(): void {

    const confirmationCode = this.userConfirmationForm.value["confirmationCode"];

    this.userConfirmationSvc.confirmUserPut(this.userID, { confirmationCode })
      .then(res => {

        this.messageSvc.add({
          severity: 'success',
          summary: 'Success',
          detail: `Successfully confirmed user with User ID: ${res.userID}
                  \nYou may now log into your account!`,
          life: 5000,
        });
        
        this.userConfirmationForm.reset();

        this.router.navigate(['/login']);
      })
      .catch(err => {

        const errorMessage = err?.error?.incorrectCode || "Unknown error occurred."

        this.messageSvc.add({
          severity: 'error',
          summary: 'Error',
          detail: errorMessage,
          life: 5000,
        });
      })
  }
}
