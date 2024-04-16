package vttp.csf.mp2.backend.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.csf.mp2.backend.models.EventCard;
import vttp.csf.mp2.backend.models.EventDetails;
import vttp.csf.mp2.backend.models.EventPage;
import vttp.csf.mp2.backend.models.EventSearch;
import vttp.csf.mp2.backend.utility.Constants;
import vttp.csf.mp2.backend.utility.EventQueries;
import vttp.csf.mp2.backend.utility.EventUtility;

@Repository
public class EventRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

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

  public EventPage retrieveEventCards(EventSearch searchParams, String sortOrder, int page, int size) {

    List<AggregationOperation> pipeline = new ArrayList<>();

    MatchOperation matchOps = Aggregation.match(eventUtils.returnMatchCriteria(searchParams));
    pipeline.add(matchOps);

    AggregationResults<EventCard> totalResults = mongoTemplate.aggregate(
        Aggregation.newAggregation(pipeline),
        eventsCollection,
        EventCard.class);
    long totalRecords = totalResults.getMappedResults().size();

    if (!sortOrder.equals("NONE")) {

      SortOperation sortOps = Aggregation.sort(
          sortOrder.equals("ASC")
              ? Sort.by("start").ascending()
              : Sort.by("start").descending()
          );
      pipeline.add(sortOps);
    }

    SkipOperation skipOps = Aggregation.skip(page * size);
    pipeline.add(skipOps);

    LimitOperation limitOps = Aggregation.limit(size);
    pipeline.add(limitOps);

    AggregationResults<EventCard> paginatedResults = mongoTemplate.aggregate(
        Aggregation.newAggregation(pipeline),
        eventsCollection,
        EventCard.class);
    List<EventCard> events = paginatedResults.getMappedResults();

    return new EventPage(events, totalRecords);
  }

  public Optional<EventDetails> retrieveEventDetails(String eventID) {

    Criteria criteria = Criteria.where("eventID").is(eventID);
    Query query = Query.query(criteria);

    return Optional.ofNullable(mongoTemplate.findOne(query, EventDetails.class, eventsCollection));
  }

  public boolean isBookmarkExists(String userID, String eventID) {

    return jdbcTemplate.queryForObject(EventQueries.SQL_CHECK_BOOKMARK_EXISTS, Integer.class, userID, eventID) > 0;
  }

  public boolean createEventBookmark(String userID, EventCard event) {

    return jdbcTemplate.update(
        EventQueries.SQL_CREATE_EVENT_BOOKMARK,
        userID,
        event.eventID(),
        event.name(),
        event.start(),
        event.logo(),
        event.venueName(),
        event.country()
    ) > 0;
  }

  public List<EventCard> retrieveEventBookmarks(String userID) {

    return jdbcTemplate.query(EventQueries.SQL_RETRIEVE_EVENT_BOOKMARKS,
        (rs, rowNum) -> 
        new EventCard(
            rs.getString("event_id"),
            rs.getString("event_name"),
            rs.getString("event_start"),
            rs.getString("event_logo"),
            rs.getString("event_venue"),
            rs.getString("event_country")
        ), userID);
  }

  public boolean removeEventBookmark(String userID, String eventID) {

    return jdbcTemplate.update(EventQueries.SQL_REMOVE_EVENT_BOOKMARK, userID, eventID) > 0;
  }

  public int retrieveEventBookmarkCount(String eventID) {

    return jdbcTemplate.queryForObject(EventQueries.SQL_RETRIEVE_EVENT_BOOKMARK_COUNT, Integer.class, eventID);
  }
}
