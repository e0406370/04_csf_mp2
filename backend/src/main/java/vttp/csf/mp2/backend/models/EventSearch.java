package vttp.csf.mp2.backend.models;

public record EventSearch(

    String eventName,
    String venueName,
    String country,
    String startAfter,
    String startBefore) {
}