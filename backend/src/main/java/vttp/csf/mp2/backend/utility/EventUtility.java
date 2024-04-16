package vttp.csf.mp2.backend.utility;

import java.util.UUID;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import vttp.csf.mp2.backend.models.EventCard;
import vttp.csf.mp2.backend.models.EventDetails;
import vttp.csf.mp2.backend.models.EventSearch;

@Component
public class EventUtility {

  public Criteria returnMatchCriteria(EventSearch searchParams) {

    Criteria criteria = Criteria.where("country").is(searchParams.country());

    if (!searchParams.eventName().isEmpty()) {
      criteria.and("name").regex(searchParams.eventName(), "i");
    }

    if (!searchParams.venueName().isEmpty()) {
      criteria.and("venueName").regex(searchParams.venueName(), "i");
    }

    if (!searchParams.startAfter().isEmpty() && !searchParams.startBefore().isEmpty()) {
      Long startAfterLong = Long.parseLong(searchParams.startAfter());
      Long startBeforeLong = Long.parseLong(searchParams.startBefore());

      String startAfterDateTime = Utils.returnDateTimeInISOFromMilliseconds(startAfterLong, true);
      String startBeforeDateTime = Utils.returnDateTimeInISOFromMilliseconds(startBeforeLong, false);

      criteria.and("start").gte(startAfterDateTime).lte(startBeforeDateTime);
    } 
    else if (!searchParams.startAfter().isEmpty()) {
      Long startAfterLong = Long.parseLong(searchParams.startAfter());

      String startAfterDateTime = Utils.returnDateTimeInISOFromMilliseconds(startAfterLong, true);

      criteria.and("start").gte(startAfterDateTime);
    } 
    else if (!searchParams.startBefore().isEmpty()) {
      Long startBeforeLong = Long.parseLong(searchParams.startBefore());

      String startBeforeDateTime = Utils.returnDateTimeInISOFromMilliseconds(startBeforeLong, false);

      criteria.and("start").lte(startBeforeDateTime);
    }

    return criteria;
  }

  public JsonObject returnEventDetailsInJson(EventDetails event) {

    return Json.createObjectBuilder()
        .add("eventID", event.eventID())
        .add("name", event.name())
        .add("description", event.description())
        .add("link", event.link())
        .add("start", event.start())
        .add("end", event.end())
        .add("created", event.created())
        .add("logo", event.logo())
        .add("venueAddress", event.venueAddress())
        .add("venueName", event.venueName())
        .add("latitude", Double.parseDouble(event.latitude()))
        .add("longitude", Double.parseDouble(event.longitude()))
        .add("country", event.country())
        .build();
  }

  public EventCard returnEventCardFromJson(JsonObject payload) {

    JsonObject eventObj = payload.getJsonObject("event");
    String eventID = eventObj.getString("eventID");
    String name = eventObj.getString("name");
    String start = eventObj.getString("start");
    String logo = eventObj.getString("logo");
    String venue = eventObj.getString("venueName");
    String country = eventObj.getString("country");

    return new EventCard(eventID, name, start, logo, venue, country);
  }

  // generate a 8-character UUID
  public String generateEventID() {

    return UUID.randomUUID().toString().substring(0, 8);
  }
}
