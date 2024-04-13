import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { EventPage } from '../models/eventcard';

@Injectable({
  providedIn: 'root'
})
  
export class EventListService {

  private httpClient = inject(HttpClient);

  public retrieveEvents(page: number, size: number): Observable<EventPage> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.httpClient.get<EventPage>("/api/events", { params: params});
  }
}
