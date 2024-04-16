import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { EventCard } from '../models/event';
import { ERROR_MESSAGE, ERROR_NOT_LOGGED_IN_MESSAGE } from '../utility/constants';
import { SessionStore } from '../utility/session.store';
import { UtilityService } from '../utility/utility.service';
import { EventRegistrationsService } from './event-registrations.service';

@Component({
  selector: 'app-event-registrations',
  templateUrl: './event-registrations.component.html',
  styleUrl: './event-registrations.component.css'
})
  
export class EventRegistrationsComponent implements OnInit {

  private router = inject(Router);
  private sessionStore = inject(SessionStore);
  private utilitySvc = inject(UtilityService);
  private eventRegistrationsSvc = inject(EventRegistrationsService);

  registeredEvents$!: Observable<EventCard[]>;

  ngOnInit(): void {

    if (!this.sessionStore.isLoggedIn()) {
      this.utilitySvc.generateErrorMessage(ERROR_NOT_LOGGED_IN_MESSAGE);
      this.router.navigate(['/login']);
    }

    const userID = this.sessionStore.getLoggedID();
    this.registeredEvents$ = this.eventRegistrationsSvc.retrieveEventRegistrations(userID);
  }

  removeEventRegistration(eventID: string): void {

    const userID = this.sessionStore.getLoggedID();
    this.eventRegistrationsSvc.removeEventRegistration(userID, eventID)
      .then(res => {
        this.utilitySvc.generateSuccessMessage(res?.message);
        this.registeredEvents$ = this.eventRegistrationsSvc.retrieveEventRegistrations(userID);
      })
      .catch(err => {
        this.utilitySvc.generateErrorMessage(err?.error?.message || ERROR_MESSAGE);
    })
  }
}
