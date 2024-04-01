package vttp.csf.mp2.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.csf.mp2.backend.models.User;
import vttp.csf.mp2.backend.services.UserService;
import vttp.csf.mp2.backend.utility.UserUtility;

@RestController
@RequestMapping(path = "/api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  @Autowired
  private UserService userSvc;

  @PostMapping(path = "/register")
  public ResponseEntity<String> registerUser(@RequestBody String registrationPayload) {

    User newUser = UserUtility.parseRegistrationPayload(registrationPayload);

    userSvc.registerUser(newUser);

    return ResponseEntity.ok("{}");
  }

}
