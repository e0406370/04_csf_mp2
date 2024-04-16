import { Component, OnInit, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { EventCard } from '../models/event';
import { SessionStore } from '../utility/session.store';
import { UtilityService } from '../utility/utility.service';
import { EventBookmarksService } from './event-bookmarks.service';

@Component({
  selector: 'app-event-bookmarks',
  templateUrl: './event-bookmarks.component.html',
  styleUrl: './event-bookmarks.component.css',
})
  
export class EventBookmarksComponent implements OnInit {

  private sessionStore = inject(SessionStore);
  private utilitySvc = inject(UtilityService);
  private eventBookmarksSvc = inject(EventBookmarksService);

  bookmarkedEvents$!: Observable<EventCard[]>;

  ngOnInit(): void {

    const userID = this.sessionStore.getLoggedID();
    this.bookmarkedEvents$ = this.eventBookmarksSvc.retrieveEventBookmarks(userID);
  }
}
