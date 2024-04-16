import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { EventCard } from '../models/event';
import { SessionStore } from '../utility/session.store';
import { UtilityService } from '../utility/utility.service';
import { EventBookmarksService } from './event-bookmarks.service';
import { ERROR_MESSAGE, ERROR_NOT_LOGGED_IN_MESSAGE } from '../utility/constants';

@Component({
  selector: 'app-event-bookmarks',
  templateUrl: './event-bookmarks.component.html',
  styleUrl: './event-bookmarks.component.css',
})
  
export class EventBookmarksComponent implements OnInit {

  private router = inject(Router);
  private sessionStore = inject(SessionStore);
  private utilitySvc = inject(UtilityService);
  private eventBookmarksSvc = inject(EventBookmarksService);

  bookmarkedEvents$!: Observable<EventCard[]>;

  ngOnInit(): void {

    if (!this.sessionStore.isLoggedIn()) {
      this.utilitySvc.generateErrorMessage(ERROR_NOT_LOGGED_IN_MESSAGE);
      this.router.navigate(['/login']);
    }

    const userID = this.sessionStore.getLoggedID();
    this.bookmarkedEvents$ = this.eventBookmarksSvc.retrieveEventBookmarks(userID);
  }

  removeEventBookmark(eventID: string): void {

    const userID = this.sessionStore.getLoggedID();
    this.eventBookmarksSvc.removeEventBookmark(userID, eventID)
      .then(res => {
        this.utilitySvc.generateSuccessMessage(res?.message);
        this.bookmarkedEvents$ = this.eventBookmarksSvc.retrieveEventBookmarks(userID);
      })
      .catch(err => {
        this.utilitySvc.generateErrorMessage(err?.error?.message || ERROR_MESSAGE);
    })
  }
}
