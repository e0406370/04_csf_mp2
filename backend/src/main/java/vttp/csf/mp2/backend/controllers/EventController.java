package vttp.csf.mp2.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vttp.csf.mp2.backend.models.EventPage;
import vttp.csf.mp2.backend.services.EventService;

@RestController
@RequestMapping(path = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

  @Autowired
  private EventService eventSvc;

  @GetMapping(path = "")
  public ResponseEntity<EventPage> retrieveEventCards(
      @RequestParam(required = true, defaultValue = "0") int page,
      @RequestParam(required = true, defaultValue = "20") int size) {
    

    System.out.println(page);
    System.out.println(size);

    EventPage events = eventSvc.retrieveEventCards(page, size);
    System.out.println(events.totalRecords());
    System.out.println(events.events());

    return ResponseEntity 
        .status(HttpStatus.OK)
        .body(eventSvc.retrieveEventCards(page, size));
  }
}