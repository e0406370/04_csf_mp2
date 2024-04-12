import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { EventCard } from '../models/eventcard';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
  
export class EventListService {

  private httpClient = inject(HttpClient);

  public retrieveEvents(): Observable<EventCard[]> {

    return this.httpClient.get<EventCard[]>("/api/events");
  }
}
