import { Injectable, inject } from '@angular/core';
import { AbstractControl, FormGroup, ValidationErrors } from '@angular/forms';
import { User } from '../models/user';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
  
export class UserRegistrationService {

  private httpClient = inject(HttpClient);

  pastDateValidator(ctrl: AbstractControl): ValidationErrors | null {

    const currentDate = new Date();
    const selectedDate = new Date(ctrl.value);

    if (currentDate < selectedDate) {

      return { notPastDate: true } as ValidationErrors;
    }

    return null;
  }

  parseRegistrationForm(userRegistrationForm: FormGroup): User {

    const newUser: User = {
      name: userRegistrationForm.get("name")?.value,
      email: userRegistrationForm.get("email")?.value,
      birthDate: new Date(userRegistrationForm.get("birthDate")?.value).getTime(),
      username: userRegistrationForm.get("username")?.value,
      password: userRegistrationForm.get("password")?.value
    }

    return newUser;
  }

  public registerUser(newUser: User): Promise<any> {

    return firstValueFrom(this.httpClient.post<any>("/api/register", newUser));
  }
}
