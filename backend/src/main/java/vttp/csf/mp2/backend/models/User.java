package vttp.csf.mp2.backend.models;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public record User(String userID, String name, String email, LocalDate birthDate, String username, String password) {

  public static User retrieveUser(SqlRowSet srs) {

    String userID = srs.getString("user_id");
    String name = srs.getString("user_name");
    String email = srs.getString("user_email");
    LocalDate birthDate = LocalDate.ofInstant(srs.getDate("user_dob").toInstant(), ZoneId.systemDefault());
    String username = srs.getString("user_username");
    String password = srs.getString("user_password");

    return new User(userID, name, email, birthDate, username, password);
  }
}
