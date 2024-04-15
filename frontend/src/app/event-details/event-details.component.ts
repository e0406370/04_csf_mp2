import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UtilityService } from '../utility/utility.service';
import { EventDetailsService } from './event-details.service';
import { ERROR_MESSAGE } from '../utility/constants';
import { EventDetails } from '../models/event';
import { EventMapService } from './event-map.service';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrl: './event-details.component.css'
})
  
export class EventDetailsComponent implements OnInit {

  private activatedRoute = inject(ActivatedRoute);
  private router = inject(Router);
  
  private utilitySvc = inject(UtilityService);
  private eventDetailsSvc = inject(EventDetailsService);
  private eventMapSvc = inject(EventMapService);

  eventID!: string;
  eventDetails!: EventDetails;

  ngOnInit(): void {

    this.activatedRoute.params.subscribe(params => {
      this.eventID = params['eventID'];

      this.eventDetailsSvc.retrieveEventDetails(this.eventID)
        .then(res => {
          
          this.eventDetails = res;
          this.loadEventMap();
        })
        .catch(err => {
          
          this.utilitySvc.generateErrorMessage(err?.error?.message || ERROR_MESSAGE);
          this.router.navigate(['/error']);
        })
    })
  }

  loadEventMap(): void {

    this.eventMapSvc.loadEventMap()
      .then(() => {

        const position = { lat: +this.eventDetails.latitude, lng: +this.eventDetails.longitude }

        new google.maps.Map(document.getElementById("map")!, {
          center: position,
          zoom: 15,
        })

        console.info('Success: Map loaded');
      })
      .catch(err => {

        console.error('Error: Map not loaded', err);
      })
  }
}
