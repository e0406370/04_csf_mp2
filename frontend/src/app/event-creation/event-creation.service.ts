import { Injectable } from '@angular/core';
import { Loader } from '@googlemaps/js-api-loader';
import { MAPS_API_KEY } from '../utility/constants';

@Injectable({
  providedIn: 'root'
})
  
export class EventCreationService {

  marker!: google.maps.Marker;

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
}
