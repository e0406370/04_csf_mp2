package vttp.csf.mp2.backend.models;

public record EventCard(
    String eventID,
    String name,
    String start,
    String logo,
    String venueName,
    String country) {
}