package vttp.csf.mp2.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.csf.mp2.backend.models.EventPage;
import vttp.csf.mp2.backend.repositories.EventRepository;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepo;

  public EventPage retrieveEventCards(int page, int size) {
    
    return eventRepo.retrieveEventCards(page, size);
  }
}
