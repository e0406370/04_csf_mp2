import { Component, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EventCard, EventSearch, INIT_SEARCH_PARAMS } from '../models/event';
import { ERROR_MESSAGE, ERROR_NOT_LOGGED_IN_MESSAGE, SELECTED_COUNTRIES } from '../utility/constants';
import { EventListService } from './event-list.service';
import { Paginator } from 'primeng/paginator';
import { SessionStore } from '../utility/session.store';
import { UtilityService } from '../utility/utility.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.css'
})
  
export class EventListComponent implements OnInit {

  @ViewChild('paginator') paginator!: Paginator;

  private sessionStore = inject(SessionStore);
  private utilitySvc = inject(UtilityService);
  private fb = inject(FormBuilder);
  eventListSvc = inject(EventListService);

  eventSearchForm!: FormGroup;
  searchParams: EventSearch = INIT_SEARCH_PARAMS;
  selectedCountries: string[] = SELECTED_COUNTRIES;

  eventList!: EventCard[];
  totalRecords!: number;
  page: number = 0;
  size: number = 20;
  sortOrder: 'ASC' | 'DESC' | 'NONE' = 'NONE';

  ngOnInit(): void {

    this.eventSearchForm = this.createEventSearchForm();
    this.loadEvents();
  }

  createEventSearchForm(): FormGroup {

    return this.fb.group({
      eventName: this.fb.control<string>(INIT_SEARCH_PARAMS.eventName),
      venueName: this.fb.control<string>(INIT_SEARCH_PARAMS.venueName),
      country: this.fb.control<string>(INIT_SEARCH_PARAMS.country),
      startAfter: this.fb.control<string>(INIT_SEARCH_PARAMS.startAfter),
      startBefore: this.fb.control<string>(INIT_SEARCH_PARAMS.startBefore),
    });
  }

  loadEvents() {

    this.eventListSvc
      .retrieveEvents(this.searchParams, this.sortOrder, this.page, this.size)
      .subscribe((data) => {

      this.eventList = data.events;
      this.totalRecords = data.totalRecords;
    });
  }

  submitEventSearchForm(): void {
  
    this.searchParams = this.eventListSvc.parseEventSearchForm(this.eventSearchForm);
    this.loadEvents();

    this.page = 0;
    this.paginator.changePage(this.page);
  }

  onPageChange(event: any): void {

    this.page = event.page;
    this.size = event.rows;
    this.loadEvents();
  }

  toggleSortOrder(sortOption: string): void {

    if (sortOption === 'ASC') {
      this.sortOrder = 'ASC';
    }
    else if (sortOption === 'DESC') {
      this.sortOrder = 'DESC';
    }
    else if (sortOption === 'NONE') {
      this.sortOrder = 'NONE';
    }
  }

  resetParams(): void {

    this.eventSearchForm = this.createEventSearchForm();
    this.sortOrder = 'NONE';
  }

  createEventBookmark(event: EventCard): void {

    if (!this.sessionStore.isLoggedIn()) {
      this.utilitySvc.generateErrorMessage(ERROR_NOT_LOGGED_IN_MESSAGE);
    }
    else {
      this.eventListSvc.createEventBookmark(this.sessionStore.getLoggedID(), event)
        .then(res => {
          this.utilitySvc.generateSuccessMessage(res?.message);
        })
        .catch(err => {
          this.utilitySvc.generateErrorMessage(err?.error?.message || ERROR_MESSAGE);
      })
    }
  }
}
