package vttp.csf.mp2.backend.utility;

import java.io.StringReader;
import java.time.LocalDate;

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

  // generate a 26-character ULID
  private String generateUserID() {

    return UlidCreator.getUlid().toLowerCase();
  }

  public User parseRegistrationPayload(String registrationPayload) {

    JsonReader r = Json.createReader(new StringReader(registrationPayload));
    JsonObject jo = r.readObject();

    String userID = generateUserID();
    String name = jo.getString("name").trim();
    String email = jo.getString("email").trim().toLowerCase();
    LocalDate birthDate = Utils.returnDateFromMilliseconds(jo.getJsonNumber("birthDate").longValue());
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

  public String parseConfirmationPayload(String confirmationPayload) {

    JsonReader r = Json.createReader(new StringReader(confirmationPayload));
    JsonObject jo = r.readObject();

    return jo.getString("confirmationCode");
  }

  public String parseEmailPayload(String emailPayload) {

    JsonReader r = Json.createReader(new StringReader(emailPayload));
    JsonObject jo = r.readObject();

    return jo.getString("id");
  }

  public boolean isCorrectMatch(String rawPassword, String hashedPassword) {

    return passwordEncoder.matches(rawPassword, hashedPassword);
  }

  public JsonObject returnUserInJson(SqlRowSet srs) {

    srs.next();

    String userID = srs.getString("user_id");
    String name = srs.getString("user_name");

    return Json.createObjectBuilder()
        .add("userID", userID)
        .add("name", name)
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
