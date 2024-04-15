package vttp.csf.mp2.backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.csf.mp2.backend.exceptions.EventException;
import vttp.csf.mp2.backend.models.EventDetails;
import vttp.csf.mp2.backend.models.EventPage;
import vttp.csf.mp2.backend.models.EventSearch;
import vttp.csf.mp2.backend.repositories.EventRepository;
import vttp.csf.mp2.backend.utility.Messages;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepo;

  public EventPage retrieveEventCards(EventSearch searchParams, String sortOrder, int page, int size) {

    return eventRepo.retrieveEventCards(searchParams, sortOrder, page, size);
  }
  
  public EventDetails retrieveEventDetails(String eventID) throws EventException {

    Optional<EventDetails> eventOpt = eventRepo.retrieveEventDetails(eventID);

    if (eventOpt.isEmpty()) {

      throw new EventException(Messages.FAILURE_EVENT_NOT_FOUND);
    }

    return eventOpt.get();
  }
}
