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
  private JdbcTemplate jdbcTemplate;

  public boolean emailExists(String email) {

    return jdbcTemplate.queryForObject(SQLQueries.SQL_CHECK_EMAIL_EXISTS, Integer.class, email) > 0;
  }

  public boolean usernameExists(String username) {

    return jdbcTemplate.queryForObject(SQLQueries.SQL_CHECK_USERNAME_EXISTS, Integer.class, username) > 0;
  }

  public String retrieveHashedPasswordByUsername(String username) {

    return jdbcTemplate.queryForObject(SQLQueries.SQL_RETRIEVE_PASSWORD_BY_USERNAME, String.class, username);
  }

  public boolean isAccountConfirmed(String username) {

    return jdbcTemplate.queryForObject(SQLQueries.SQL_CHECK_CONFIRMATION_STATUS, Boolean.class, username);
  }
  
  public boolean registerUser(User newUser) {

    int registered = jdbcTemplate.update(
        SQLQueries.SQL_REGISTER_USER,
        newUser.userID(),
        newUser.name(),
        newUser.email(),
        newUser.birthDate(),
        newUser.username(),
        newUser.password()
    );

    return registered > 0;
  }

  public boolean loginUser(String username) {

    return jdbcTemplate.update(SQLQueries.SQL_LOGIN_USER, username) > 0;
  }
  
  public SqlRowSet retrieveDetailsByUsername(String username) {

    return jdbcTemplate.queryForRowSet(SQLQueries.SQL_RETRIEVE_DETAILS_BY_USERNAME, username);
  }

  public boolean deleteUser(String userID) {

    int deleted = jdbcTemplate.update(SQLQueries.SQL_DELETE_USER, userID);

    return deleted > 0;
  }

  public boolean confirmUser(String userID) {

    int confirmed = jdbcTemplate.update(SQLQueries.SQL_CONFIRM_USER, userID);

    return confirmed > 0;
  }
}
