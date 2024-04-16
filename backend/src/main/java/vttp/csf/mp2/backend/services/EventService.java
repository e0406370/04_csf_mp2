package vttp.csf.mp2.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;

import vttp.csf.mp2.backend.exceptions.EventException;
import vttp.csf.mp2.backend.models.EventCard;
import vttp.csf.mp2.backend.models.EventDetails;
import vttp.csf.mp2.backend.models.EventPage;
import vttp.csf.mp2.backend.models.EventSearch;
import vttp.csf.mp2.backend.repositories.EventRepository;
import vttp.csf.mp2.backend.utility.Messages;
import vttp.csf.mp2.backend.utility.Utils;

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

  public void createEventBookmark(String payload) throws EventException {

    JsonObject jsonPayload = Utils.returnPayloadInJson(payload);
    String eventID = jsonPayload.getString("eventID");
    String userID = jsonPayload.getString("userID");

    if (eventRepo.isBookmarkExists(eventID, userID)) {
      throw new EventException(Messages.FAILURE_EVENT_BOOKMARK_EXISTS);
    }

    eventRepo.createEventBookmark(eventID, userID);
  }

  public List<EventCard> retrieveEventBookmarks(String userID) {

    return eventRepo.retrieveEventBookmarks(userID);
  }
}
