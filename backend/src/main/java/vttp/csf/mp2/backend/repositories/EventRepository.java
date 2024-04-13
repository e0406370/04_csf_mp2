package vttp.csf.mp2.backend.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
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
        .append("longitude", event.longitude())
        .append("country", event.country());

    mongoTemplate.insert(eventDoc, eventsCollection);

    return eventDoc;
  }
  
  public List<Event> retrieveEvents() {

    MatchOperation matchOps = Aggregation.match(Criteria.where("country").is("Taiwan"));

    SortOperation sortOps = Aggregation.sort(Sort.by(Direction.ASC, "start"));

    Aggregation pipeline = Aggregation.newAggregation(matchOps, sortOps);

    AggregationResults<Event> results = mongoTemplate.aggregate(pipeline, eventsCollection, Event.class);

    return results.getMappedResults();
  }
}
