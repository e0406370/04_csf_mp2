import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
  
export class UserProfileService {

  private activatedRoute = inject(ActivatedRoute);
  private httpClient = inject(HttpClient);

  public retrieveUserProfile(): Promise<any> {

    const userID = this.activatedRoute.snapshot.params['userID'];

    return firstValueFrom(this.httpClient.get<any>(`/api/user/profile/${userID}`));
  }
}
