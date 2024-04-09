import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { ERROR_MESSAGE } from '../utility/constants';
import { UtilityService } from '../utility/utility.service';
import { UserConfirmationService } from './user-confirmation.service';

@Component({
  selector: 'app-user-confirmation',
  templateUrl: './user-confirmation.component.html',
  styleUrl: './user-confirmation.component.css'
})
  
export class UserConfirmationComponent implements OnInit {

  private fb = inject(FormBuilder);
  private router = inject(Router);

  private utilitySvc = inject(UtilityService);
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
    // console.info(this.userConfirmationForm.value["confirmationCode"]);
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

        this.utilitySvc.generateSuccessMessage(res.message);
        this.userConfirmationForm.reset();

        this.router.navigate(['/login']);
      })
      .catch(err => {

        this.utilitySvc.generateErrorMessage(err?.error?.message || ERROR_MESSAGE);
      })
  }
}
