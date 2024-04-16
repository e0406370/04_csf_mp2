import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { UtilityService } from '../utility/utility.service';
import { EventCreationService } from './event-creation.service';
import { SessionStore } from '../utility/session.store';

@Component({
  selector: 'app-event-creation',
  templateUrl: './event-creation.component.html',
  styleUrl: './event-creation.component.css'
})
  
export class EventCreationComponent implements OnInit {

  private router = inject(Router);
  private sessionStore = inject(SessionStore);
  private utilitySvc = inject(UtilityService);
  private eventCreationSvc = inject(EventCreationService);

  ngOnInit(): void {
    
    this.eventCreationSvc.loadAutoCompleteMap();
  }

}
