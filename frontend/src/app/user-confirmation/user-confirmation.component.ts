import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
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
      .then(result => {

        alert(`Successfully confirmed user with User ID: ${result.userID}\nYou may now log into your account!`);
        this.userConfirmationForm.reset();

        this.router.navigate(['/login']);
      })
      .catch(error => {

        if (error.incorrectCode) {
          alert(`Error: ${error.incorrectCode}`);
        }

        alert(`Error: ${JSON.stringify(error)}`);
      })
  }
}
