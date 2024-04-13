package vttp.csf.mp2.backend.utility;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import vttp.csf.mp2.backend.models.EventSearch;

@Component
public class EventUtility {

  public Criteria returnMatchCriteria(EventSearch searchParams) {

    LocalDate startAfter = Utils.returnDateFromMilliseconds(Long.parseLong(searchParams.startAfter()));
    LocalDate startBefore = Utils.returnDateFromMilliseconds(Long.parseLong(searchParams.startBefore()));

    Criteria criteria = Criteria
        .where("name").regex(searchParams.eventName(), "i")
        .and("venueName").regex(searchParams.venueName(), "i")
        .and("country").is(searchParams.country())
        .and("start").gte(startAfter).lte(startBefore);

    return criteria;
  }

}
