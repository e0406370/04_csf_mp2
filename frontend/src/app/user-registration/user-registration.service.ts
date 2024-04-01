import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
  
export class UserRegistrationService {

  pastDateValidator(ctrl: AbstractControl): ValidationErrors | null {

    const currentDate = new Date();
    const selectedDate = new Date(ctrl.value);

    if (currentDate < selectedDate) {

      return { notPastDate: true } as ValidationErrors;
    }

    return null;
  }

  
}
