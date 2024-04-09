package vttp.csf.mp2.backend.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import vttp.csf.mp2.backend.models.Event;
import vttp.csf.mp2.backend.utility.Constants;

@Repository
public class EventRepository {

  @Autowired
  @Qualifier(Constants.BEAN_MONGO)
  private MongoTemplate mongoTemplate;

  @Value("${mongo.collection.events}")
  private String eventsCollection;

  public Document storeEvent(Event event) {

    Document eventDoc = new Document();

    eventDoc 
        .append("eventID", event.eventID())
        .append("name", event.name())
        .append("description", event.description())
        .append("link", event.link())
        .append("start", event.start())
        .append("end", event.end())
        .append("created", event.created())
        .append("logo", event.logo())
        .append("venueAddress", event.venueAddress())
        .append("venueName", event.venueName())
        .append("latitude", event.latitude())
        .append("longitude", event.longitude());

    mongoTemplate.insert(eventDoc, eventsCollection);

    return eventDoc;
  }

}
