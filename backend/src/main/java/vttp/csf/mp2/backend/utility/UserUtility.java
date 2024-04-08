package vttp.csf.mp2.backend.utility;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.json.Json;
import jakarta.json.JsonObject;

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

    JsonObject jo = Utils.returnPayloadInJson(registrationPayload);

    String userID = generateUserID();
    String name = jo.getString("name").trim();
    String email = jo.getString("email").trim().toLowerCase();
    LocalDate birthDate = Utils.returnDateFromMilliseconds(jo.getJsonNumber("birthDate").longValue());
    String username = jo.getString("username").trim();
    String encodedPassword = passwordEncoder.encode(jo.getString("password"));

    return new User(userID, name, email, birthDate, username, encodedPassword);
  }

  public LoginDetails parseLoginPayload(String loginPayload) {

    JsonObject jo = Utils.returnPayloadInJson(loginPayload);

    String username = jo.getString("username").trim();
    String rawPassword = jo.getString("password");

    return new LoginDetails(username, rawPassword);
  }

  public String parseEmailPayload(String emailPayload) {

    JsonObject jo = Utils.returnPayloadInJson(emailPayload);

    return jo.getString("id");
  }

  public String parseConfirmationPayload(String confirmationPayload) {

    JsonObject jo = Utils.returnPayloadInJson(confirmationPayload);

    return jo.getString("confirmationCode");
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

  public JsonObject returnUserProfileInJson(String userID, SqlRowSet srs) {

    srs.next();

    String name = srs.getString("user_name");
    String email = srs.getString("user_email");
    Long birthDate = srs.getDate("user_dob").getTime();
    Long createdDate = srs.getDate("created_date").getTime();

    return Json.createObjectBuilder()
        .add("userID", userID)
        .add("name", name)
        .add("email", email)
        .add("birthDate", birthDate)
        .add("createdDate", createdDate)
        .build();
  }
}
