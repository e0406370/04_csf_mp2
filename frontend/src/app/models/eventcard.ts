export interface EventCard {
  
  eventID: string;
  name: string;
  start: string;
  logo: string;
  venueName: string;
  country: string;
}

export interface EventPage {

  events: EventCard[];
  totalRecords: number;
}