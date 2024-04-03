package vttp.csf.mp2.backend.utility;

import java.io.StringReader;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import vttp.csf.mp2.backend.models.LoginDetails;
import vttp.csf.mp2.backend.models.User;

@Component
public class UserUtility {

  @Autowired
  private PasswordEncoder passwordEncoder;

  // 26 characters long
  private String generateUserID() {

    return UlidCreator.getUlid().toLowerCase();
  }

  // 6 digits long from 000000 to 999999
  public String generateConfirmationToken() {

    SecureRandom rnd = new SecureRandom();
    int num = rnd.nextInt(1000000);

    return String.format("%06d", num);
  }

  private LocalDate parseDate(Long longDateValue) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");

    String date = Instant.ofEpochMilli(longDateValue)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter);

    return LocalDate.parse(date);
  }

  public User parseRegistrationPayload(String registrationPayload) {

    JsonReader r = Json.createReader(new StringReader(registrationPayload));
    JsonObject jo = r.readObject();

    String userID = generateUserID();
    String name = jo.getString("name").trim();
    String email = jo.getString("email").trim().toLowerCase();
    LocalDate birthDate = parseDate(jo.getJsonNumber("birthDate").longValue());
    String username = jo.getString("username").trim();
    String encodedPassword = passwordEncoder.encode(jo.getString("password"));

    return new User(userID, name, email, birthDate, username, encodedPassword);
  }

  public LoginDetails parseLoginPayload(String loginPayload) {

    JsonReader r = Json.createReader(new StringReader(loginPayload));
    JsonObject jo = r.readObject();

    String username = jo.getString("username").trim();
    String rawPassword = jo.getString("password");

    return new LoginDetails(username, rawPassword);
  }

  public boolean isCorrectMatch(String rawPassword, String hashedPassword) {

    return passwordEncoder.matches(rawPassword, hashedPassword);
  }

  public JsonObject retrieveUserInJson(SqlRowSet srs) {

    srs.next();

    String userID = srs.getString("user_id");
    String name = srs.getString("user_name");
    String email = srs.getString("user_email");
    Long birthDate = srs.getDate("user_dob").getTime();
    String username = srs.getString("user_username");
    String password = srs.getString("user_password");

    return Json.createObjectBuilder()
        .add("userID", userID)
        .add("name", name)
        .add("email", email)
        .add("birthDate", birthDate)
        .add("username", username)
        .add("password", password)
        .build();
  }

  public ResponseEntity<String> createErrorResponse(HttpStatus status, String errorName, String errorMessage) {

    JsonObject errorResponse = Json.createObjectBuilder()
        .add(errorName, errorMessage)
        .build();

    return ResponseEntity
        .status(status)
        .body(errorResponse.toString());
  }
}
