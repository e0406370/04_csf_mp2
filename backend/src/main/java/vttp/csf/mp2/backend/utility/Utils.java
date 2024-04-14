package vttp.csf.mp2.backend.utility;

import java.io.StringReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Utils {

  public static String returnCurrentTimeStamp() {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN);

    return LocalDateTime.now().format(formatter);
  }

  public static LocalDate returnDateFromMilliseconds(Long longDateValue) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN);

    String date = Instant.ofEpochMilli(longDateValue)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter);

    return LocalDate.parse(date);
  }

  public static String returnDateTimeInISOFromMilliseconds(long longDateValue, boolean isDateAfter) {

    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    LocalDateTime dateTime = Instant.ofEpochMilli(longDateValue)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();

    if (isDateAfter) {
      dateTime = dateTime.withHour(0).withMinute(0).withSecond(0);
    } 
    else {
      dateTime = dateTime.withHour(23).withMinute(59).withSecond(59);
    }

    return dateTime.format(formatter);
  }

  public static JsonObject returnMessageInJson(String message) {

    return Json.createObjectBuilder()
        .add("message", message)
        .add("timestamp", Utils.returnCurrentTimeStamp())
        .build();
  }

  public static JsonObject returnResponseInJson(JsonObject response) {

    return Json.createObjectBuilder()
        .add("response", response)
        .build();
  }

  public static JsonObject returnMessageWithResponseInJson(String message, JsonObject response) {

    return Json.createObjectBuilder()
        .add("message", message)
        .add("timestamp", Utils.returnCurrentTimeStamp())
        .add("response", response)
        .build();
  }

  public static JsonObject returnPayloadInJson(String payload) {

    return Json.createReader(new StringReader(payload)).readObject();
  }
}
