import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EventCard } from '../models/eventcard';
import { SELECTED_COUNTRIES } from '../utility/constants';
import { EventListService } from './event-list.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.css'
})
  
export class EventListComponent implements OnInit {

  private fb = inject(FormBuilder);
  private eventListSvc = inject(EventListService);

  eventSearchForm!: FormGroup;
  selectedCountry: string = 'Singapore';
  selectedCountries: string[] = SELECTED_COUNTRIES;

  eventList!: EventCard[];
  totalRecords!: number;
  page: number = 0;
  size: number = 20;

  ngOnInit(): void {

    this.eventSearchForm = this.createEventSearchForm();
    this.loadEvents(this.page, this.size, this.selectedCountry);
  }

  createEventSearchForm(): FormGroup {

    return this.fb.group({
      eventName: this.fb.control<string>(''),
      venueName: this.fb.control<string>(''),
      country: this.fb.control<string>('Singapore'),
      startAfter: this.fb.control<Date>(new Date()),
      startBefore: this.fb.control<Date>(new Date()),
      sortDateOrder: this.fb.control<string>('ASC'),
    })
  }

  submitEventSearchForm(): void {
  
    const searchParams = this.eventListSvc.parseEventSearchForm(this.eventSearchForm);
    this.selectedCountry = searchParams.country;

    this.loadEvents(this.page, this.size, this.selectedCountry);
  }


  loadEvents(page: number, size: number, country: string) {

    this.eventListSvc.retrieveEvents(page, size, country).subscribe((data) => {

      this.eventList = data.events;
      this.totalRecords = data.totalRecords;
    });
  }

  onPageChange(event: any): void {

    this.page = event.page;
    this.size = event.rows;

    this.loadEvents(this.page, this.size, this.selectedCountry);
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
