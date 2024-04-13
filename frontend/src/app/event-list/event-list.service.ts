import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { EventPage, EventSearch } from '../models/event';

@Injectable({
  providedIn: 'root'
})
  
export class EventListService {

  private httpClient = inject(HttpClient);

  public retrieveEvents(searchParams: EventSearch, page: number, size: number): Observable<EventPage> {

    const params = new HttpParams()
      .set('eventName', searchParams.eventName)
      .set('venueName', searchParams.venueName)
      .set('country', searchParams.country)
      .set('startAfter', searchParams.startAfter.getTime().toString())
      .set('startBefore', searchParams.startBefore.getTime().toString())
      .set('page', page)
      .set('size', size);
    
    console.info(params);

    return this.httpClient.get<EventPage>("/api/events", { params: params });
  }

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

  simplifyVenueName(venueName: string): string {

    const indicators: string[] = ['remote', 'online', 'webinar', 'virtual', 'zoom'];

    for (const indicator of indicators) {

      if (venueName.toLowerCase().includes(indicator)) {
        return 'Online Event';
      }
    }
    return venueName;
  }
}
