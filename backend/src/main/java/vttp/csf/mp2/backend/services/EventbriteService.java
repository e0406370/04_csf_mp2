package vttp.csf.mp2.backend.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonObject;

import vttp.csf.mp2.backend.models.Event;
import vttp.csf.mp2.backend.repositories.EventRepository;
import vttp.csf.mp2.backend.utility.Utils;

@Service
public class EventbriteService {

  @Autowired
  private EventRepository eventRepo;

  @Value("${eventbrite.api.url}")
  private String EVENTBRITE_API_URL;

  @Value("${eventbrite.api.key}")
  private String EVENTBRITE_API_KEY;

  @Value("${eventbrite.api.oauth.token}")
  private String EVENTBRITE_API_OAUTH_TOKEN;

  private RestTemplate restTemplate = new RestTemplate();

  private Logger logger = Logger.getLogger(EventbriteService.class.getName());

  public void retrieveEventbriteData(String eventID) {

    String requestUrl = UriComponentsBuilder
        .fromUriString(EVENTBRITE_API_URL)
        .path("events/")
        .path(eventID)
        .queryParam("expand", "venue")
        .queryParam("token", EVENTBRITE_API_OAUTH_TOKEN)
        .toUriString();

    RequestEntity<Void> request = RequestEntity.get(requestUrl).build();

    try {

      ResponseEntity<String> response = restTemplate.exchange(request, String.class);
      JsonObject jsonPayload = Utils.returnPayloadInJson(response.getBody());

      storeEventbriteData(eventID, jsonPayload);

      logger.info("Event added with eventID: %s".formatted(eventID));
    } 
    catch (Exception e) {

      System.err.println(e.getMessage());
      logger.severe("An error occurred while storing the event with eventID: %s".formatted(eventID));
    }
  }

  private void storeEventbriteData(String eventID, JsonObject jsonPayload) {

    String name = jsonPayload.getJsonObject("name").getString("text");
    String description = jsonPayload.getJsonObject("description").getString("text");
    String link = jsonPayload.getString("url");
    String start = jsonPayload.getJsonObject("start").getString("local");
    String end = jsonPayload.getJsonObject("end").getString("local");
    String created = jsonPayload.getString("created");
    String logo = jsonPayload.getJsonObject("logo").getString("url");
    String venueAddress = jsonPayload.getJsonObject("venue").getJsonObject("address").getString("localized_address_display");
    String venueName = jsonPayload.getJsonObject("venue").getString("name");
    String latitude = jsonPayload.getJsonObject("venue").getString("latitude");
    String longitude = jsonPayload.getJsonObject("venue").getString("longitude");

    eventRepo.storeEvent(new Event(eventID, name, description, link, start, end, created, logo, venueAddress, venueName, latitude, longitude));
  }
  
  // https://www.eventbrite.sg/e/market-insights-conference-tickets-859261382927
  private String retrieveIDFromLink(String eventLink) {

    String[] parts = eventLink.split("-");

    return parts[parts.length - 1];
  }

  public void retrieveEventbriteDataInBulk() {

    String scrapeFile = new File("src/main/java/vttp/csf/mp2/backend/_scrape/links/scrape_id_singapore.txt").getAbsolutePath();

    try (BufferedReader br = new BufferedReader(new FileReader(scrapeFile))) {

      br.lines()
        .map(eventLink -> retrieveIDFromLink(eventLink))
        .forEach(eventID -> retrieveEventbriteData(eventID));
    } 
    catch (Exception e) {
      
      e.printStackTrace();
    }
  }
}
