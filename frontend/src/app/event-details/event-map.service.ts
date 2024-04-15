import { Injectable } from '@angular/core';
import { Loader } from '@googlemaps/js-api-loader';
import { MAPS_API_KEY } from '../utility/constants';

@Injectable({
  providedIn: 'root'
})
  
export class EventMapService {

  private loader = new Loader(
    {
      apiKey: MAPS_API_KEY
    }
  );

  public loadEventMap(): Promise<any> {
    
    return this.loader.importLibrary('maps');
  }
}
