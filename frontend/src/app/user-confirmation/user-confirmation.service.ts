import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
  
export class UserConfirmationService {

  private httpClient = inject(HttpClient);

  public confirmUserGet(userID: string): Promise<any> {

    return firstValueFrom(this.httpClient.get<any>(`/api/confirm/${userID}`));
  }

  public confirmUserPut(userID: string, confirmationCode: string): Promise<any> {

    return firstValueFrom(this.httpClient.put<any>(`/api/confirm/${userID}`, confirmationCode));
  }
}