package vttp.csf.mp2.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import vttp.csf.mp2.backend.exceptions.AuthenticationFailureException;
import vttp.csf.mp2.backend.exceptions.EmailExistsException;
import vttp.csf.mp2.backend.exceptions.UsernameExistsException;
import vttp.csf.mp2.backend.models.LoginDetails;
import vttp.csf.mp2.backend.models.User;
import vttp.csf.mp2.backend.services.ApplicationMetricsService;
import vttp.csf.mp2.backend.services.UserService;
import vttp.csf.mp2.backend.utility.UserUtility;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  @Autowired
  private UserService userSvc;

  @Autowired
  private UserUtility userUtils;

  @Autowired
  private ApplicationMetricsService appMetrics;

  @PostMapping(path = "/register")
  public ResponseEntity<String> registerUser(@RequestBody String registrationPayload) {

    User newUser = userUtils.parseRegistrationPayload(registrationPayload);

    try {
      userSvc.registerUser(newUser);
      appMetrics.incrementRegister();

      JsonObject registrationSuccessResponse = Json.createObjectBuilder()
          .add("userID", newUser.userID())
          .build();

      return ResponseEntity
          .status(HttpStatus.CREATED) // 201 CREATED
          .body(registrationSuccessResponse.toString());
    }

    catch (EmailExistsException e) {
      return userUtils.createErrorResponse(HttpStatus.CONFLICT, "emailExists", e.getMessage()); // 409 CONFLICT
    }

    catch (UsernameExistsException e) {
      return userUtils.createErrorResponse(HttpStatus.CONFLICT, "usernameExists", e.getMessage()); // 409 CONFLICT
    }
  }

  @PostMapping(path = "/login")
  public ResponseEntity<String> loginUser(@RequestBody String loginPayload) {

    LoginDetails login = userUtils.parseLoginPayload(loginPayload);

    try {
      JsonObject loginSuccessResponse = userSvc.loginUser(login);
      appMetrics.incrementLogin();

      return ResponseEntity
          .status(HttpStatus.OK) // 200 OK
          .body(loginSuccessResponse.toString());
    }

    catch (AuthenticationFailureException e) {
      return userUtils.createErrorResponse(HttpStatus.UNAUTHORIZED, "authenticationFailure", e.getMessage()); // 401 UNAUTHORIZED
    }
  }

  @GetMapping(path = "/confirm/{userID}")
  public ResponseEntity<String> confirmUserGet(@PathVariable String userID) {

    if (!userSvc.isUnconfirmedUserID(userID)) {

      String notFound = "User ID %s not found among unconfirmed accounts!".formatted(userID);
      return userUtils.createErrorResponse(HttpStatus.NOT_FOUND, "notFound", notFound); // 404 NOT FOUND
    }

    JsonObject confirmationResponse = Json.createObjectBuilder()
      .add("userID", userID)
      .build();

    return ResponseEntity
        .status(HttpStatus.OK) // 200 OK
        .body(confirmationResponse.toString());
  }

  @PutMapping(path = "/confirm/{userID}")
  public ResponseEntity<String> confirmUserPut(@PathVariable String userID, @RequestBody String confirmationPayload) {

    String confirmationCode = userUtils.parseConfirmationPayload(confirmationPayload);

    if (!userSvc.isCorrectConfirmationCode(userID, confirmationCode)) {

      String incorrectCode = "Incorrect confirmation code!";
      return userUtils.createErrorResponse(HttpStatus.BAD_REQUEST, "incorrectCode", incorrectCode); // 400 BAD REQUEST
    }

    userSvc.confirmUser(userID);
    appMetrics.incrementConfirm();
    
    JsonObject confirmationResponse = Json.createObjectBuilder()
      .add("userID", userID)
      .build();

    return ResponseEntity
        .status(HttpStatus.OK) // 200 OK
        .body(confirmationResponse.toString());
  }
}
