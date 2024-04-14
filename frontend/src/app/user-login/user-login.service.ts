import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { firstValueFrom } from 'rxjs';

import { LoginDetails } from '../models/user';


@Injectable({
  providedIn: 'root'
})
  
export class UserLoginService {

  private httpClient = inject(HttpClient);

  parseLoginForm(userLoginForm: FormGroup): LoginDetails {

    const login: LoginDetails = {

      username: userLoginForm.get("username")?.value,
      password: userLoginForm.get("password")?.value
    }

    return login;
  }

  public loginUser(login: LoginDetails): Promise<any> {

    return firstValueFrom(this.httpClient.post<any>("/api/user/login", login));
  }
}
