import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
  
export class UserProfileService {

  private httpClient = inject(HttpClient);

  public retrieveUserProfile(userID: string): Promise<any> {

    return firstValueFrom(this.httpClient.get<any>(`/api/user/profile/${userID}`));
  }
}
