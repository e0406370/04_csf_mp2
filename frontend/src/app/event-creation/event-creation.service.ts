import { Injectable, inject } from '@angular/core';
import { Loader } from '@googlemaps/js-api-loader';
import { MAPS_API_KEY } from '../utility/constants';
import { AbstractControl, FormGroup, ValidationErrors } from '@angular/forms';
import { CreationDetails, Place } from '../models/creation';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
  
export class EventCreationService {

  marker!: google.maps.Marker;
  place!: Place;
  cannotSubmit: boolean = true;

  private httpClient = inject(HttpClient);

  private loader = new Loader(
    {
      apiKey: MAPS_API_KEY,
    }
  );

  private loadAutoCompleteMapLibraries(): Promise<any> {

    const maps = this.loader.importLibrary('maps');
    const marker = this.loader.importLibrary('marker');
    const places = this.loader.importLibrary('places');

    return Promise.all([maps, marker, places]);
  }

  public loadAutoCompleteMap(): void {

    this.loadAutoCompleteMapLibraries()
      .then(() => {

        const position = {
          lat: 40.749933,
          lng: -73.98633,
        };

        const map = new google.maps.Map(document.getElementById('map')!, {
          center: position,
          zoom: 13,
          mapTypeControl: false,
        })

        //@ts-ignore
        const placeAutoComplete = new google.maps.places.PlaceAutocompleteElement();
        //@ts-ignore
        placeAutoComplete.id = 'place-autocomplete-input';

        const card = document.getElementById('place-autocomplete-card') as HTMLElement;
        //@ts-ignore
        card.appendChild(placeAutoComplete);
        map.controls[google.maps.ControlPosition.TOP_LEFT].push(card);

        this.marker = new google.maps.Marker({
          map: map,
          position: position
        })

        const infoWindow = new google.maps.InfoWindow({});

        //@ts-ignore
        placeAutoComplete.addEventListener('gmp-placeselect', async ({ place }) => {
          await place.fetchFields({ fields: ['displayName', 'formattedAddress', 'location'] });

          if (place.viewport) {
            map.fitBounds(place.viewport);
          }
          else {
            map.setCenter(place.location);
            map.setZoom(17);
          }

          let content = '<div id="infowindow-content">' +
          '<span id="place-displayname" class="title">' + place.displayName + '</span><br />' +
          '<span id="place-address">' + place.formattedAddress + '</span>' +
          '</div>';
          
          this.updateInfoWindow(content, place.location);
          this.marker.setPosition(place.location);

          // important
          this.place = {
            venue: place.displayName,
            address: place.formattedAddress,
            latitude: place.location.lat(),
            longitude: place.location.lng(),
          }
          this.cannotSubmit = false;
          console.info(this.place);
          console.info(this.cannotSubmit);

          console.info('Place location: ', place.location.lat(), place.location.lng());
          console.info('Place location: ', this.marker.getPosition()?.lat(), this.marker.getPosition()?.lng());
        })

        console.info('Success: Map loaded');
      })
      .catch((err) => {

        console.error('Error: Map not loaded', err);
      })
  }

  private updateInfoWindow(content: string, center: google.maps.LatLng): void {

    const infoWindow = new google.maps.InfoWindow({});
    
    infoWindow.setContent(content);
    infoWindow.setPosition(center);
    infoWindow.open({
      map: this.marker.getMap(),
      anchor: this.marker,
      shouldFocus: false,
    });
  }

  futureDateValidator(ctrl: AbstractControl): ValidationErrors | null {

    const currentDate = new Date();
    const selectedDate = new Date(ctrl.value);

    if (currentDate > selectedDate) {

      return { notFutureDate: true } as ValidationErrors;
    }

    return null;
  }

  parseEventCreationForm(eventCreationForm: FormGroup): any {

    const creation: CreationDetails = {

      name: eventCreationForm.get("name")?.value,
      description: eventCreationForm.get("description")?.value,
      logo: eventCreationForm.get("logo")?.value || "https://deae.jp/wp-content/uploads/2018/06/tapple-shashin-nosetakunai.png",
      start: eventCreationForm.get("start")?.value,
      end: eventCreationForm.get("end")?.value,
    }

    const place: Place = this.place;

    return { creation, place };
  }

  public createEvent(details: any): Promise<any> {

    return firstValueFrom(this.httpClient.post<any>("/api/events/create", details));
  } 
}
