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
import vttp.csf.mp2.backend.utility.EventUtility;
import vttp.csf.mp2.backend.utility.Messages;
import vttp.csf.mp2.backend.utility.Utils;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepo;

  @Autowired
  private EventUtility eventUtils;

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
    String userID = jsonPayload.getString("userID");
    EventCard event = eventUtils.returnEventCardFromJson(jsonPayload);

    if (eventRepo.isBookmarkExists(userID, event.eventID())) {
      throw new EventException(Messages.FAILURE_EVENT_BOOKMARK_EXISTS);
    }

    eventRepo.createEventBookmark(userID, event);
  }

  public List<EventCard> retrieveEventBookmarks(String userID) {

    return eventRepo.retrieveEventBookmarks(userID);
  }

  public void removeEventBookmark(String payload) {

    JsonObject jsonPayload = Utils.returnPayloadInJson(payload);
    String userID = jsonPayload.getString("userID");
    String eventID = jsonPayload.getString("eventID");

    eventRepo.removeEventBookmark(userID, eventID);
  }

  public int retrieveEventBookmarkCount(String eventID) {

    return eventRepo.retrieveEventBookmarkCount(eventID);
  }

  public void createEventRegistration(String payload) throws EventException {

    JsonObject jsonPayload = Utils.returnPayloadInJson(payload);
    String userID = jsonPayload.getString("userID");
    EventCard event = eventUtils.returnEventCardFromJson(jsonPayload);

    if (eventRepo.isRegistrationExists(userID, event.eventID())) {
      throw new EventException(Messages.FAILURE_EVENT_REGISTRATION_EXISTS);
    }

    eventRepo.createEventRegistration(userID, event);
  }
  
  public List<EventCard> retrieveEventRegistrations(String userID) {

    return eventRepo.retrieveEventRegistrations(userID);
  }

  public void removeEventRegistration(String payload) {

    JsonObject jsonPayload = Utils.returnPayloadInJson(payload);
    String userID = jsonPayload.getString("userID");
    String eventID = jsonPayload.getString("eventID");

    eventRepo.removeEventRegistration(userID, eventID);
  }

  public List<String> retrieveEventRegistrationAttendees(String eventID) {

    return eventRepo.retrieveEventRegistrationAttendees(eventID);
  }

  public String createEvent(String payload) {

    JsonObject jsonPayload = Utils.returnPayloadInJson(payload);

    JsonObject creationObj = jsonPayload.getJsonObject("creation");
    String name = creationObj.getString("name");
    String description = creationObj.getString("description");
    String logo = creationObj.getString("logo");
    String start = creationObj.getString("start");
    String end = creationObj.getString("end");

    JsonObject placeObj = jsonPayload.getJsonObject("place");
    String venue = placeObj.getString("venue");
    String address = placeObj.getString("address");
    String latitude = String.valueOf(placeObj.getJsonNumber("latitude").doubleValue());
    String longitude = String.valueOf(placeObj.getJsonNumber("longitude").doubleValue());

    String eventID = eventUtils.generateEventID();
    String link = "https://www.eventbrite.sg/e/mayday-sg-festival-2024-presented-by-merchcow-tickets-858621248267"; // placeholder link
    String created = Utils.returnCurrentTimeStamp();
    String country = "Singapore"; // default country

    EventDetails event = new EventDetails(eventID, name, description, link, start, end, created, logo, address, venue, latitude, longitude, country);
    
    System.out.println(event);
    eventRepo.storeEventDetails(event);

    return eventID;
  }
}
