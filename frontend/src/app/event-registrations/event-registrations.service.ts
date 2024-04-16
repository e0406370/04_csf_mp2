import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, firstValueFrom } from 'rxjs';
import { EventCard } from '../models/event';

@Injectable({
  providedIn: 'root'
})
  
export class EventRegistrationsService {

  private httpClient = inject(HttpClient);

  public retrieveEventRegistrations(userID: string): Observable<EventCard[]> {

    return this.httpClient.get<EventCard[]>(`/api/events/registrations/${userID}`);
  }

  public removeEventRegistration(userID: string, eventID: string): Promise<any> {

    return firstValueFrom(this.httpClient.post<any>("/api/events/registrations/remove", { userID, eventID }));
  }
}
