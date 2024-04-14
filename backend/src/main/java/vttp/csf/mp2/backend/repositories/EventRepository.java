package vttp.csf.mp2.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.csf.mp2.backend.models.EventCard;
import vttp.csf.mp2.backend.models.EventDetails;
import vttp.csf.mp2.backend.models.EventPage;
import vttp.csf.mp2.backend.models.EventSearch;
import vttp.csf.mp2.backend.utility.Constants;
import vttp.csf.mp2.backend.utility.EventUtility;

@Repository
public class EventRepository {

  @Autowired
  @Qualifier(Constants.BEAN_MONGO)
  private MongoTemplate mongoTemplate;

  @Value("${mongo.collection.events}")
  private String eventsCollection;

  @Autowired
  private EventUtility eventUtils;

  public Document storeEventDetails(EventDetails event) {

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

  public EventPage retrieveEventCards(EventSearch searchParams, int page, int size) {

    MatchOperation matchOps = Aggregation.match(eventUtils.returnMatchCriteria(searchParams));

    AggregationResults<EventCard> totalResults = mongoTemplate.aggregate(
        Aggregation.newAggregation(matchOps),
        eventsCollection,
        EventCard.class);
    long totalRecords = totalResults.getMappedResults().size();

    SortOperation sortOps = Aggregation.sort(Sort.by(Direction.ASC, "start"));
    SkipOperation skipOps = Aggregation.skip(page * size);
    LimitOperation limitOps = Aggregation.limit(size);

    AggregationResults<EventCard> paginatedResults = mongoTemplate.aggregate(
        Aggregation.newAggregation(matchOps, sortOps, skipOps, limitOps),
        eventsCollection,
        EventCard.class);
    List<EventCard> events = paginatedResults.getMappedResults();

    return new EventPage(events, totalRecords);
  }

  public Optional<EventDetails> retrieveEventDetails(String eventID) {

    Criteria criteria = Criteria.where("eventID").is(eventID);
    Query query = Query.query(criteria);

    return Optional.ofNullable(mongoTemplate.find(query, EventDetails.class, eventID).getFirst());
  }
}
