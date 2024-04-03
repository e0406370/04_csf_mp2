package vttp.csf.mp2.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.csf.mp2.backend.exceptions.AuthenticationFailureException;
import vttp.csf.mp2.backend.exceptions.EmailExistsException;
import vttp.csf.mp2.backend.exceptions.UsernameExistsException;
import vttp.csf.mp2.backend.models.LoginDetails;
import vttp.csf.mp2.backend.models.User;
import vttp.csf.mp2.backend.repositories.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepo;

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

  public boolean loginUser(LoginDetails login) throws AuthenticationFailureException {

    if (!userRepo.usernameExists(login.username())) {

      String errorMessage = "Username not found in database!";
      throw new AuthenticationFailureException(errorMessage);
    }

    return false;
  }
}
