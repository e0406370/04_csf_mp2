package vttp.csf.mp2.backend.models;

import java.util.List;

public record EventPage(List<EventCard> events, long totalRecords) {
}
