import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
  
export class EventDetailsService {

  private httpClient = inject(HttpClient);

  public retrieveEventDetails(eventID: string): Promise<any> {

    return firstValueFrom(this.httpClient.get<any>(`/api/events/${eventID}`));
  }

  public retrieveEventBookmarkCount(eventID: string): Promise<any> {

    return firstValueFrom(this.httpClient.get<any>(`/api/events/bookmark/count/${eventID}`));
  }
}
