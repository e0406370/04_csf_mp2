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

  ngOnInit(): void {

    this.loadEvents(0, 20);
  }

  loadEvents(page: number, size: number) {

    this.eventListSvc.retrieveEvents(page, size).subscribe((data) => {

      this.eventList = data.events;
      this.totalRecords = data.totalRecords;
    });
  }

  onPageChange(event: any): void {

    const page = event.page;
    const size = event.rows;

    console.info(page);
    console.info(size);

    this.loadEvents(page, size);
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
