package vttp.csf.mp2.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.csf.mp2.backend.models.User;
import vttp.csf.mp2.backend.utility.UserQueries;

@Repository
public class UserRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public boolean userExists(String userID) {

    return jdbcTemplate.queryForObject(UserQueries.SQL_CHECK_USER_EXISTS, Integer.class, userID) > 0;
  }

  public boolean emailExists(String email) {

    return jdbcTemplate.queryForObject(UserQueries.SQL_CHECK_EMAIL_EXISTS, Integer.class, email) > 0;
  }

  public boolean usernameExists(String username) {

    return jdbcTemplate.queryForObject(UserQueries.SQL_CHECK_USERNAME_EXISTS, Integer.class, username) > 0;
  }

  public boolean registerUser(User newUser) {

    int registered = jdbcTemplate.update(
        UserQueries.SQL_REGISTER_USER,
        newUser.userID(),
        newUser.name(),
        newUser.email(),
        newUser.birthDate(),
        newUser.username(),
        newUser.password()
    );

    return registered > 0;
  }

  public String retrieveHashedPasswordByUsername(String username) {

    return jdbcTemplate.queryForObject(UserQueries.SQL_RETRIEVE_PASSWORD_BY_USERNAME, String.class, username);
  }

  public boolean isAccountConfirmedByUsername(String username) {

    return jdbcTemplate.queryForObject(UserQueries.SQL_CHECK_CONFIRMATION_STATUS_BY_USERNAME, Boolean.class, username);
  }
  
  public boolean isAccountConfirmedByUserID(String userID) {

    return jdbcTemplate.queryForObject(UserQueries.SQL_CHECK_CONFIRMATION_STATUS_BY_USER_ID, Boolean.class, userID);
  }
  
  public boolean loginUser(String username) {

    return jdbcTemplate.update(UserQueries.SQL_LOGIN_USER, username) > 0;
  }
  
  public SqlRowSet retrieveUserByUsername(String username) {

    return jdbcTemplate.queryForRowSet(UserQueries.SQL_RETRIEVE_USER_BY_USERNAME, username);
  }

  public boolean deleteUser(String userID) {

    int deleted = jdbcTemplate.update(UserQueries.SQL_DELETE_USER, userID);

    return deleted > 0;
  }

  public boolean confirmUser(String userID) {

    int confirmed = jdbcTemplate.update(UserQueries.SQL_CONFIRM_USER, userID);

    return confirmed > 0;
  }

  public SqlRowSet retrieveUserProfile(String userID) {

    return jdbcTemplate.queryForRowSet(UserQueries.SQL_RETRIEVE_PROFILE_BY_USER_ID, userID);
  }
}
