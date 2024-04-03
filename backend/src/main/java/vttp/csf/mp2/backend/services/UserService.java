package vttp.csf.mp2.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;

import vttp.csf.mp2.backend.exceptions.AuthenticationFailureException;
import vttp.csf.mp2.backend.exceptions.EmailExistsException;
import vttp.csf.mp2.backend.exceptions.UsernameExistsException;
import vttp.csf.mp2.backend.models.LoginDetails;
import vttp.csf.mp2.backend.models.User;
import vttp.csf.mp2.backend.repositories.UserRepository;
import vttp.csf.mp2.backend.utility.UserUtility;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private UserUtility userUtils;

  public boolean registerUser(User newUser) throws EmailExistsException, UsernameExistsException {

    if (userRepo.emailExists(newUser.email())) {

      String errorMessage = "Email already exists in database!";
      throw new EmailExistsException(errorMessage);
    }

    if (userRepo.usernameExists(newUser.username())) {

      String errorMessage = "Username already exists in database!";
      throw new UsernameExistsException(errorMessage);
    }

    return userRepo.registerUser(newUser);
  }

  public JsonObject loginUser(LoginDetails login) throws AuthenticationFailureException {

    String username = login.username();

    if (!userRepo.usernameExists(username)) {

      String errorMessage = "Username not found in database!";
      throw new AuthenticationFailureException(errorMessage);
    }

    String rawPassword = login.password();
    String hashedPassword = userRepo.retrieveHashedPasswordByUsername(username);

    if (!userUtils.isCorrectMatch(rawPassword, hashedPassword)) {

      String errorMessage = "Incorrect password!";
      throw new AuthenticationFailureException(errorMessage);
    }

    // if (!userRepo.isAccountConfirmed(username)) {

    //   String errorMessage = "Account has not been confirmed!";
    //   throw new AuthenticationFailureException(errorMessage);
    // }

    userRepo.loginUser(username);
    return userUtils.retrieveUserInJson(userRepo.retrieveDetailsByUsername(username));
  }
}
