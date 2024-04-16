package vttp.csf.mp2.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import vttp.csf.mp2.backend.exceptions.EventException;
import vttp.csf.mp2.backend.models.EventCard;
import vttp.csf.mp2.backend.models.EventDetails;
import vttp.csf.mp2.backend.models.EventPage;
import vttp.csf.mp2.backend.models.EventSearch;
import vttp.csf.mp2.backend.services.ApplicationMetricsService;
import vttp.csf.mp2.backend.services.EventService;
import vttp.csf.mp2.backend.utility.EventUtility;
import vttp.csf.mp2.backend.utility.Messages;
import vttp.csf.mp2.backend.utility.Utils;

@RestController
@RequestMapping(path = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

  @Autowired
  private EventService eventSvc;

  @Autowired
  private EventUtility eventUtils;

  @Autowired
  private ApplicationMetricsService appMetricsSvc;

  @GetMapping(path = "")
  public ResponseEntity<EventPage> retrieveEventCards(
      @RequestParam(required = false) String eventName,
      @RequestParam(required = false) String venueName,
      @RequestParam(required = false) String country,
      @RequestParam(required = false) String startAfter,
      @RequestParam(required = false) String startBefore,
      @RequestParam(required = true, defaultValue = "NONE") String sortOrder,
      @RequestParam(required = true, defaultValue = "0") int page,
      @RequestParam(required = true, defaultValue = "20") int size) {

    EventSearch searchParams = new EventSearch(eventName, venueName, country, startAfter, startBefore);

    EventPage events = eventSvc.retrieveEventCards(searchParams, sortOrder, page, size);
    appMetricsSvc.incrementEventSearchMetric();

    return ResponseEntity
        .status(HttpStatus.OK) // 200 OK
        .body(events);
  }

  @GetMapping(path = "/{eventID}")
  public ResponseEntity<String> retrieveEventDetails(@PathVariable String eventID) {

    try {
      EventDetails event = eventSvc.retrieveEventDetails(eventID);
      JsonObject response = eventUtils.returnEventDetailsInJson(event);

      return ResponseEntity
          .status(HttpStatus.OK) // 200 OK
          .body(Utils.returnResponseInJson(response).toString());
    }

    catch (EventException e) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND) // 404 NOT FOUND
          .body(Utils.returnMessageInJson(e.getMessage()).toString());
    }
  }

  @PostMapping(path = "/bookmark")
  public ResponseEntity<String> createEventBookmark(@RequestBody String payload) {

    try {
      eventSvc.createEventBookmark(payload);

      return ResponseEntity
          .status(HttpStatus.CREATED) // 201 CREATED
          .body(Utils.returnMessageInJson(Messages.SUCCESS_EVENT_CREATE_BOOKMARK).toString());
    }

    catch (EventException e) {
      return ResponseEntity
          .status(HttpStatus.CONFLICT) // 409 CONFLICT
          .body(Utils.returnMessageInJson(e.getMessage()).toString());
    }
  }

  @GetMapping(path = "/bookmarks/{userID}")
  public ResponseEntity<List<EventCard>> retrieveEventBookmarks(@PathVariable String userID) {

    List<EventCard> bookmarkedEvents = eventSvc.retrieveEventBookmarks(userID);

    return ResponseEntity
        .status(HttpStatus.OK) // 200 OK
        .body(bookmarkedEvents);
  }

  @PostMapping(path = "/bookmarks/remove")
  public ResponseEntity<String> removeEventBookmark(@RequestBody String payload) {

    eventSvc.removeEventBookmark(payload);

    return ResponseEntity
        .status(HttpStatus.OK) // 200 OK
        .body(Utils.returnMessageInJson(Messages.SUCCESS_EVENT_REMOVE_BOOKMARK).toString());
  }

  @GetMapping(path = "/bookmark/count/{eventID}")
  public ResponseEntity<String> retrieveEventBookmarkCount(@PathVariable String eventID) {

    int count = eventSvc.retrieveEventBookmarkCount(eventID);

    return ResponseEntity
        .status(HttpStatus.OK) // 200 OK
        .body(String.valueOf(count));
  }

  @PostMapping(path = "/register")
  public ResponseEntity<String> createEventRegistration(@RequestBody String payload) {

    try {
      eventSvc.createEventRegistration(payload);

      return ResponseEntity
          .status(HttpStatus.CREATED) // 201 CREATED
          .body(Utils.returnMessageInJson(Messages.SUCCESS_EVENT_CREATE_REGISTRATION).toString());
    }

    catch (EventException e) {
      return ResponseEntity
          .status(HttpStatus.CONFLICT) // 409 CONFLICT
          .body(Utils.returnMessageInJson(e.getMessage()).toString());
    }
  }

  @GetMapping(path = "/registrations/{userID}")
  public ResponseEntity<List<EventCard>> retrieveEventRegistrations(@PathVariable String userID) {

    List<EventCard> registeredEvents = eventSvc.retrieveEventRegistrations(userID);

    return ResponseEntity
        .status(HttpStatus.OK) // 200 OK
        .body(registeredEvents);
  }

  @PostMapping(path = "/registrations/remove")
  public ResponseEntity<String> removeEventRegistration(@RequestBody String payload) {

    eventSvc.removeEventRegistration(payload);

    return ResponseEntity
        .status(HttpStatus.OK) // 200 OK
        .body(Utils.returnMessageInJson(Messages.SUCCESS_EVENT_REMOVE_REGISTRATION).toString());
  }

  @GetMapping(path = "/register/attendees/{eventID}")
  public ResponseEntity<List<String>> retrieveEventRegistrationAttendees(@PathVariable String eventID) {

    List<String> attendees = eventSvc.retrieveEventRegistrationAttendees(eventID);

    return ResponseEntity
        .status(HttpStatus.OK) // 200 OK
        .body(attendees);
  }

  @PostMapping(path = "/create")
  public ResponseEntity<String> createEvent(@RequestBody String payload) {

    String eventID = eventSvc.createEvent(payload);
    JsonObject response = Json.createObjectBuilder().add("eventID", eventID).build();

    return ResponseEntity
      .status(HttpStatus.OK) // 201 CREATED
      .body(Utils.returnMessageWithResponseInJson(Messages.SUCCESS_EVENT_CREATION, response).toString());
  }
}
