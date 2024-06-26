import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable, firstValueFrom } from 'rxjs';
import { EventCard, EventPage, EventSearch } from '../models/event';

@Injectable({
  providedIn: 'root'
})
  
export class EventListService {

  private httpClient = inject(HttpClient);

  public retrieveEvents(searchParams: EventSearch, sortOrder: string, page: number, size: number): Observable<EventPage> {

    const params = new HttpParams()
      .set('eventName', searchParams.eventName)
      .set('venueName', searchParams.venueName)
      .set('country', searchParams.country)
      .set('startAfter', searchParams.startAfter)
      .set('startBefore', searchParams.startBefore)
      .set('sortOrder', sortOrder)
      .set('page', page)
      .set('size', size);

    return this.httpClient.get<EventPage>("/api/events", { params: params });
  }

  parseEventSearchForm(eventSearchForm: FormGroup): EventSearch {

    const startAfter = !isNaN(Date.parse(eventSearchForm.get("startAfter")?.value))
      ? Date.parse(eventSearchForm.get("startAfter")?.value).toString()
      : '';
    
    const startBefore = !isNaN(Date.parse(eventSearchForm.get("startBefore")?.value))
      ? Date.parse(eventSearchForm.get("startBefore")?.value).toString()
      : '';

    const searchParams: EventSearch = {

      eventName: eventSearchForm.get("eventName")?.value,
      venueName: eventSearchForm.get("venueName")?.value,
      country: eventSearchForm.get("country")?.value,
      startAfter: startAfter,
      startBefore: startBefore,
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

  public createEventBookmark(userID: string, event: EventCard) {

    return firstValueFrom(this.httpClient.post<any>("/api/events/bookmark", { userID, event }));
  }

  public createEventRegistration(userID: string, event: EventCard) {

    return firstValueFrom(this.httpClient.post<any>("/api/events/register", { userID, event }));
  }
}
