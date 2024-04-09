import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
  
export class UserConfirmationService {

  private httpClient = inject(HttpClient);

  userID!: string;

  public confirmUserGet(userID: string): Promise<any> {

    return firstValueFrom(this.httpClient.get<any>(`/api/user/confirm/${userID}`));
  }

  public confirmUserPut(userID: string, confirmationCode: any): Promise<any> {

    return firstValueFrom(this.httpClient.put<any>(`/api/user/confirm/${userID}`, confirmationCode));
  }
}
