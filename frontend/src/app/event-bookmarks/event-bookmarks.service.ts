import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { EventCard } from '../models/event';
import { Observable, firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
  
export class EventBookmarksService {

  private httpClient = inject(HttpClient);

  public retrieveEventBookmarks(userID: string): Observable<EventCard[]> {

    return this.httpClient.get<EventCard[]>(`/api/events/bookmarks/${userID}`);
  }

  public removeEventBookmark(userID: string, eventID: string): Promise<any> {

    return firstValueFrom(this.httpClient.post<any>("/api/events/bookmarks/remove", { userID, eventID }));
  }
}
