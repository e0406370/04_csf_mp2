package vttp.csf.mp2.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.csf.mp2.backend.models.Event;
import vttp.csf.mp2.backend.services.EventService;

@RestController
@RequestMapping(path = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

  @Autowired
  private EventService eventSvc;

  @GetMapping(path = "")
  public ResponseEntity<List<Event>> retrieveEvents() {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(eventSvc.retrieveEvents());
  }

}
