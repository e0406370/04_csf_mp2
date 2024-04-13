import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { EventPage } from '../models/eventcard';
import { FormGroup } from '@angular/forms';
import { EventSearch } from '../models/eventsearch';

@Injectable({
  providedIn: 'root'
})
  
export class EventListService {

  private httpClient = inject(HttpClient);

  parseEventSearchForm(eventSearchForm: FormGroup): EventSearch {

    const searchParams: EventSearch = {

      eventName: eventSearchForm.get("eventName")?.value,
      venueName: eventSearchForm.get("venueName")?.value,
      country: eventSearchForm.get("country")?.value,
      startAfter: new Date(eventSearchForm.get("startAfter")?.value),
      startBefore: new Date(eventSearchForm.get("startBefore")?.value),
    }

    return searchParams;
  }


  public retrieveEvents(page: number, size: number, country: string): Observable<EventPage> {

    const params = new HttpParams()
      .set('country', country)
      .set('page', page)
      .set('size', size);

    return this.httpClient.get<EventPage>("/api/events", { params: params});
  }
}
