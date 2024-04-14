import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EventCard, EventSearch, INIT_SEARCH_PARAMS } from '../models/event';
import { SELECTED_COUNTRIES } from '../utility/constants';
import { EventListService } from './event-list.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.css'
})
  
export class EventListComponent implements OnInit {

  private fb = inject(FormBuilder);
  eventListSvc = inject(EventListService);

  eventSearchForm!: FormGroup;
  searchParams: EventSearch = INIT_SEARCH_PARAMS;
  selectedCountries: string[] = SELECTED_COUNTRIES;

  eventList!: EventCard[];
  totalRecords!: number;
  page: number = 0;
  size: number = 20;

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
    })
  }

  loadEvents() {

    this.eventListSvc.retrieveEvents(this.searchParams, this.page, this.size).subscribe((data) => {

      this.eventList = data.events;
      this.totalRecords = data.totalRecords;
    });
  }

  submitEventSearchForm(): void {
  
    this.searchParams = this.eventListSvc.parseEventSearchForm(this.eventSearchForm);
    this.loadEvents();
  }

  onPageChange(event: any): void {

    this.page = event.page;
    this.size = event.rows;
    this.loadEvents();
  }
}
