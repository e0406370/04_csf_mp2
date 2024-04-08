import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
  
export class AuthenticationService {

  private httpClient = inject(HttpClient);

  userID!: string;

  public validateUserID(endpoint: string, userID: string): Promise<any> {

    return firstValueFrom(this.httpClient.get<any>(`/api/user/${endpoint}/${userID}`));
  }

  public confirmUserPut(userID: string, confirmationCode: any): Promise<any> {

    return firstValueFrom(this.httpClient.put<any>(`/api/user/confirm/${userID}`, confirmationCode));
  }
}
