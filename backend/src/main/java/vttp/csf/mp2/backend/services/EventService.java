package vttp.csf.mp2.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.csf.mp2.backend.models.Event;
import vttp.csf.mp2.backend.repositories.EventRepository;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepo;

  public List<Event> retrieveEvents() {
    
    return eventRepo.retrieveEvents();
  }
}
