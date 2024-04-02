package vttp.csf.mp2.backend.utility;

import java.io.StringReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import vttp.csf.mp2.backend.models.User;

@Component
public class UserUtility {

  // 26 characters long
  public static String generateUserID() {

    return UlidCreator.getUlid().toLowerCase();
  }

  public static LocalDate parseDate(Long longDateValue) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");

    String date = Instant.ofEpochMilli(longDateValue)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter);

    return LocalDate.parse(date);
  }

  public static User parseRegistrationPayload(String registrationPayload) {

    JsonReader r = Json.createReader(new StringReader(registrationPayload));
    JsonObject jo = r.readObject();

    String userID = UserUtility.generateUserID();
    String name = jo.getString("name").trim();
    String email = jo.getString("email").trim().toLowerCase();
    LocalDate birthDate = UserUtility.parseDate(jo.getJsonNumber("birthDate").longValue());
    String username = jo.getString("username").trim();
    String password = jo.getString("password");

    return new User(userID, name, email, birthDate, username, password);
  }

  public static ResponseEntity<String> createErrorResponse(HttpStatus status, String errorName, String errorMessage) {

    JsonObject errorResponse = Json.createObjectBuilder()
        .add(errorName, errorMessage)
        .build();

    return ResponseEntity
        .status(status)
        .body(errorResponse.toString());
  }
}
