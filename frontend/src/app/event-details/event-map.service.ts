import { Injectable } from '@angular/core';
import { Loader } from '@googlemaps/js-api-loader';
import { MAPS_API_KEY } from '../utility/constants';

@Injectable({
  providedIn: 'root',
})
  
export class EventMapService {

  private loader = new Loader(
    {
      apiKey: MAPS_API_KEY,
    }
  );

  private loadEventMapLibraries(): Promise<any> {

    const maps = this.loader.importLibrary('maps');
    const marker = this.loader.importLibrary('marker');

    return Promise.all([maps, marker]);
  }

  public loadEventMap(latitude: string, longitude: string): void {

    this.loadEventMapLibraries()
      .then(() => {

        const position = {
          lat: +latitude,
          lng: +longitude,
        };

        const map = new google.maps.Map(document.getElementById('map')!, {
          center: position,
          zoom: 16,
        });

        const marker = new google.maps.Marker({
          map: map,
          position: position,
        });

        console.info('Success: Map loaded');
      })
      .catch((err) => {

        console.error('Error: Map not loaded', err);
      });
  }
}