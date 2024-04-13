import { Component, OnInit, inject } from '@angular/core';
import { EventListService } from './event-list.service';
import { Observable } from 'rxjs';
import { EventCard } from '../models/eventcard';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.css'
})
  
export class EventListComponent implements OnInit {

  private eventListSvc = inject(EventListService);

  eventList$!: Observable<EventCard[]>;

  ngOnInit(): void {

    this.eventList$ = this.eventListSvc.retrieveEvents();
  }

  simplifyVenueName(venueName: string): string {

    const indicators: string[] = ['online', 'virtual', 'zoom'];

    for (const indicator of indicators) {

      if (venueName.toLowerCase().includes(indicator)) {
        return 'Online Event';
      }
    }
    return venueName;
  }
}
