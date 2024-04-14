package vttp.csf.mp2.backend.utility;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

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
}
