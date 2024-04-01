package vttp.csf.mp2.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.csf.mp2.backend.models.User;
import vttp.csf.mp2.backend.repositories.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepo;
  
  public boolean registerUser(User newUser) {

    return userRepo.registerUser(newUser);
  }
}
