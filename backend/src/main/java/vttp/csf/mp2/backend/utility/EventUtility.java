package vttp.csf.mp2.backend.utility;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import vttp.csf.mp2.backend.models.EventSearch;

@Component
public class EventUtility {

  public Criteria returnMatchCriteria(EventSearch searchParams) {

    Criteria criteria = Criteria.where("country").is(searchParams.country());

    System.out.println(criteria.toString());
    System.out.println("main");

    if (!searchParams.eventName().isEmpty()) {
      criteria.and("name").regex(searchParams.eventName(), "i");
      System.out.println(criteria);
      System.out.println("event");
    }

    if (!searchParams.venueName().isEmpty()) {
      criteria.and("venueName").regex(searchParams.venueName(), "i");
      System.out.println(criteria);
      System.out.println("venue");
    }

    if (!searchParams.startAfter().isEmpty() && !searchParams.startBefore().isEmpty()) {
      Long startAfterLong = Long.parseLong(searchParams.startAfter());
      Long startBeforeLong = Long.parseLong(searchParams.startBefore());

      String startAfterDateTime = Utils.returnDateTimeFromMilliseconds(startAfterLong, true);
      String startBeforeDateTime = Utils.returnDateTimeFromMilliseconds(startBeforeLong, false);

      System.out.println(startAfterDateTime);
      System.out.println(startBeforeDateTime);

      criteria.and("start").gte(startAfterDateTime).lte(startBeforeDateTime);
    }
    else if (!searchParams.startAfter().isEmpty()) {
      Long startAfterLong = Long.parseLong(searchParams.startAfter());
      String startAfterDateTime = Utils.returnDateTimeFromMilliseconds(startAfterLong, true);

      System.out.println(startAfterDateTime);

      criteria.and("start").gte(startAfterDateTime);
    } 
    else if (!searchParams.startBefore().isEmpty()) {
      Long startBeforeLong = Long.parseLong(searchParams.startBefore());
      String startBeforeDateTime = Utils.returnDateTimeFromMilliseconds(startBeforeLong, false);

      System.out.println(startBeforeDateTime);

      criteria.and("start").lte(startBeforeDateTime);
    }

    return criteria;
  }
}
