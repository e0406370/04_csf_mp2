package vttp.csf.mp2.backend.models;

public record EventDetails(
    
    String eventID,
    String name,
    String description,
    String link,
    String start,
    String end,
    String created,
    String logo,
    String venueAddress,
    String venueName,
    String latitude,
    String longitude,
    String country) {
}
