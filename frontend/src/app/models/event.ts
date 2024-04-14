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
