export interface EventSearch {

  eventName: string;
  venueName: string;
  country: string;
  startAfter: string;
  startBefore: string;
};

export const INIT_SEARCH_PARAMS: EventSearch = {

  eventName: '',
  venueName: '',
  country: 'Singapore',
  startAfter: '',
  startBefore: '',
};

export interface EventCard {
  
  eventID: string;
  name: string;
  start: string;
  logo: string;
  venueName: string;
  country: string;
};

export interface EventPage {

  events: EventCard[];
  totalRecords: number;
};

export interface EventDetails {

  eventID: string;
  name: string;
  description: string;
  link: string;
  start: string;
  end: string;
  created: string;
  logo: string;
  venueAddress: string;
  venueName: string;
  latitude: number;
  longitude: number;
  country: string;
}
