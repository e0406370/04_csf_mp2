import { Component, OnInit, inject } from '@angular/core';
import { EventCard } from '../models/eventcard';
import { EventListService } from './event-list.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.css'
})
  
export class EventListComponent implements OnInit {

  private eventListSvc = inject(EventListService);

  eventList!: EventCard[];
  totalRecords!: number;

  page: number = 0;
  size: number = 20;

  ngOnInit(): void {

    this.loadEvents(this.page, this.size);
  }

  loadEvents(page: number, size: number) {

    this.eventListSvc.retrieveEvents(page, size).subscribe((data) => {

      this.eventList = data.events;
      this.totalRecords = data.totalRecords;
    });
  }

  onPageChange(event: any): void {

    this.page = event.page;
    this.size = event.rows;

    this.loadEvents(this.page, this.size);
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
