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
}
