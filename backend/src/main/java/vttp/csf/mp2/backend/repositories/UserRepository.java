package vttp.csf.mp2.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.csf.mp2.backend.models.User;
import vttp.csf.mp2.backend.utility.SQLQueries;

@Repository
public class UserRepository {

  @Autowired
  private JdbcTemplate template;

  public boolean emailExists(String email) {

    return template.queryForObject(SQLQueries.SQL_CHECK_EMAIL_EXISTS, Integer.class, email) > 0;
  }

  public boolean usernameExists(String username) {

    return template.queryForObject(SQLQueries.SQL_CHECK_USERNAME_EXISTS, Integer.class, username) > 0;
  }

  public String retrieveHashedPasswordByUsername(String username) {

    return template.queryForObject(SQLQueries.SQL_RETRIEVE_PASSWORD_BY_USERNAME, String.class, username);
  }

  public boolean isAccountConfirmed(String username) {

    return template.queryForObject(SQLQueries.SQL_CHECK_CONFIRMATION_STATUS, Boolean.class, username);
  }

  public boolean registerUser(User newUser) {

    int registered = template.update(
        SQLQueries.SQL_REGISTER_USER,
        newUser.userID(),
        newUser.name(),
        newUser.email(),
        newUser.birthDate(),
        newUser.username(),
        newUser.password());

    return registered > 0;
  }

  public boolean loginUser(String username) {

    return template.update(SQLQueries.SQL_LOGIN_USER, username) > 0;
  }
  
  public SqlRowSet retrieveDetailsByUsername(String username) {

    return template.queryForRowSet(SQLQueries.SQL_RETRIEVE_DETAILS_BY_USERNAME, username);
  }
}
