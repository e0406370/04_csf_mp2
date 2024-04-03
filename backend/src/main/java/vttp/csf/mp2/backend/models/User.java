package vttp.csf.mp2.backend.models;

import java.time.LocalDate;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public record User(String userID, String name, String email, LocalDate birthDate, String username, String password) {

  public static JsonObject retrieveUserInJson(SqlRowSet srs) {

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
}
