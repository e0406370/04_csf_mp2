package vttp.csf.mp2.backend.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonObject;

import vttp.csf.mp2.backend.models.EventDetails;
import vttp.csf.mp2.backend.repositories.EventRepository;
import vttp.csf.mp2.backend.utility.Utils;

@Service
public class EventbriteService {

  // modify this for each country
  private final String selectedCountry = "singapore";

  @Autowired
  private EventRepository eventRepo;

  @Value("${eventbrite.api.url}")
  private String EVENTBRITE_API_URL;

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

    eventRepo.storeEventDetails(new EventDetails(eventID, name, description, link, start, end, created, logo, venueAddress, venueName, latitude, longitude, returnProperCase(selectedCountry)));
  }
  
  public void retrieveEventbriteDataInBulk() {

    String pathName = "src/main/java/vttp/csf/mp2/backend/_scrape/links/scrape_id_%s.txt".formatted(selectedCountry);

    String scrapeFile = new File(pathName).getAbsolutePath();

    try (BufferedReader br = new BufferedReader(new FileReader(scrapeFile))) {

      br.lines()
          .map(eventLink -> retrieveIDFromLink(eventLink))
          .distinct()
          .forEach(eventID -> retrieveEventbriteData(eventID));
    } 
    catch (Exception e) {

      e.printStackTrace();
    }
  }
  
  // https://www.eventbrite.sg/e/market-insights-conference-tickets-859261382927
  // https://www.eventbrite.ch/e/858309315267
  private String retrieveIDFromLink(String eventLink) {

    String[] initialParts = eventLink.split("/");
    String[] finalParts = initialParts[initialParts.length - 1].split("-");

    return finalParts[finalParts.length - 1];
  }
  
  private String returnProperCase(String country) {

    return String.join(" ", Arrays.stream(country.split("-"))
        .map(part -> part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase())
        .toArray(String[]::new)).trim();
  }
}
