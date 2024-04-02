package vttp.csf.mp2.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import vttp.csf.mp2.backend.exceptions.EmailExistsException;
import vttp.csf.mp2.backend.exceptions.UsernameExistsException;
import vttp.csf.mp2.backend.models.User;
import vttp.csf.mp2.backend.services.UserService;
import vttp.csf.mp2.backend.utility.UserUtility;

@RestController
@RequestMapping(path = "/api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  @Autowired
  private UserService userSvc;

  @Autowired
  private UserUtility userUtils;

  @PostMapping(path = "/register")
  public ResponseEntity<String> registerUser(@RequestBody String registrationPayload) {

    User newUser = userUtils.parseRegistrationPayload(registrationPayload);

    try {
      userSvc.registerUser(newUser);

      JsonObject registrationSuccessResponse = Json.createObjectBuilder()
          .add("userID", newUser.userID())
          .build();

      return ResponseEntity
          .status(HttpStatus.CREATED)
          .body(registrationSuccessResponse.toString());
    }

    catch (EmailExistsException e) {
      return userUtils.createErrorResponse(HttpStatus.CONFLICT, "emailExists", e.getMessage());
    }

    catch (UsernameExistsException e) {
      return userUtils.createErrorResponse(HttpStatus.CONFLICT, "usernameExists", e.getMessage());
    }
  }
}
