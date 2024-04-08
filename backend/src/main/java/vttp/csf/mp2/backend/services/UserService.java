package vttp.csf.mp2.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;

import vttp.csf.mp2.backend.exceptions.UserException;
import vttp.csf.mp2.backend.models.LoginDetails;
import vttp.csf.mp2.backend.models.User;
import vttp.csf.mp2.backend.repositories.TokenRepository;
import vttp.csf.mp2.backend.repositories.UserRepository;
import vttp.csf.mp2.backend.utility.Messages;
import vttp.csf.mp2.backend.utility.UserUtility;

@Service
public class UserService {

  @Autowired
  private MailService mailSvc;

  @Autowired
  private TokenRepository tokenRepo;

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private UserUtility userUtils;

  public boolean registerUser(User newUser) throws UserException {

    if (userRepo.emailExists(newUser.email())) {

      throw new UserException(Messages.FAILURE_EMAIL_EXISTS);
    }

    if (userRepo.usernameExists(newUser.username())) {
      
      throw new UserException(Messages.FAILURE_USERNAME_EXISTS);
    }

    boolean registered = userRepo.registerUser(newUser);
    if (registered) {

      String confirmationToken = tokenRepo.saveConfirmationToken(newUser.userID());
      tokenRepo.addUserID(newUser.userID());
      
      mailSvc.sendConfirmationEmail(newUser, confirmationToken);
    }

    return registered;
  }

  public JsonObject loginUser(LoginDetails login) throws UserException {

    String username = login.username();

    if (!userRepo.usernameExists(username)) {

      throw new UserException(Messages.FAILURE_USERNAME_NOT_FOUND);
    }

    String rawPassword = login.password();
    String hashedPassword = userRepo.retrieveHashedPasswordByUsername(username);

    if (!userUtils.isCorrectMatch(rawPassword, hashedPassword)) {

      throw new UserException(Messages.FAILURE_INCORRECT_PASSWORD);
    }

    if (!userRepo.isAccountConfirmed(username)) {

      throw new UserException(Messages.FAILURE_ACCOUNT_NOT_CONFIRMED);
    }

    userRepo.loginUser(username);
    return userUtils.returnUserInJson(userRepo.retrieveUserByUsername(username));
  }

  public boolean isUnconfirmedUserID(String userID) {

    return tokenRepo.isUnconfirmedUserID(userID);
  }

  public boolean isCorrectConfirmationCode(String userID, String confirmationCode) {

    return tokenRepo.isCorrectConfirmationCode(userID, confirmationCode);
  }

  public void confirmUser(String userID) {

    boolean confirmed = userRepo.confirmUser(userID);

    if (confirmed) {

      tokenRepo.removeUserID(userID);
      tokenRepo.deleteConfirmationToken(userID);
    }
  }
}
