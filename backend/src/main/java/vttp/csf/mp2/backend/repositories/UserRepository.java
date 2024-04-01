package vttp.csf.mp2.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.csf.mp2.backend.models.User;
import vttp.csf.mp2.backend.utility.SQLQueries;

@Repository
public class UserRepository {

  @Autowired
  private JdbcTemplate template;

  public boolean registerUser(User newUser) {

    int registered = template.update(
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

}
