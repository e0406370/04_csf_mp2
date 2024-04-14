package vttp.csf.mp2.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vttp.csf.mp2.backend.exceptions.EventException;
import vttp.csf.mp2.backend.models.EventDetails;
import vttp.csf.mp2.backend.models.EventPage;
import vttp.csf.mp2.backend.models.EventSearch;
import vttp.csf.mp2.backend.services.ApplicationMetricsService;
import vttp.csf.mp2.backend.services.EventService;
import vttp.csf.mp2.backend.utility.Utils;

@RestController
@RequestMapping(path = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

  @Autowired
  private EventService eventSvc;

  @Autowired
  private ApplicationMetricsService appMetricsSvc;

  @GetMapping(path = "")
  public ResponseEntity<EventPage> retrieveEventCards(
      @RequestParam(required = false) String eventName,
      @RequestParam(required = false) String venueName,
      @RequestParam(required = false) String country,
      @RequestParam(required = false) String startAfter,
      @RequestParam(required = false) String startBefore,
      @RequestParam(required = true, defaultValue = "0") int page,
      @RequestParam(required = true, defaultValue = "20") int size) {

    EventSearch searchParams = new EventSearch(eventName, venueName, country, startAfter, startBefore);

    EventPage events = eventSvc.retrieveEventCards(searchParams, page, size);
    appMetricsSvc.incrementEventSearchMetric();

    return ResponseEntity
        .status(HttpStatus.OK) // 200 OK
        .body(events);
  }

  @GetMapping(path = "/{eventID}")
  public ResponseEntity<?> retrieveEventDetails(@PathVariable String eventID) {

    try {

      EventDetails event = eventSvc.retrieveEventDetails(eventID);

      return ResponseEntity
          .status(HttpStatus.OK) // 200 OK
          .body(event);
    }

    catch (EventException e) {

      return ResponseEntity
          .status(HttpStatus.NOT_FOUND) // 404 NOT FOUND
          .body(Utils.returnMessageInJson(e.getMessage()).toString());
    }
  }
}
