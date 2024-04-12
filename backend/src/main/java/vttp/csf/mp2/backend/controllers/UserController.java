package vttp.csf.mp2.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;

import vttp.csf.mp2.backend.exceptions.UserException;
import vttp.csf.mp2.backend.models.LoginDetails;
import vttp.csf.mp2.backend.models.User;
import vttp.csf.mp2.backend.services.ApplicationMetricsService;
import vttp.csf.mp2.backend.services.UserService;
import vttp.csf.mp2.backend.utility.Messages;
import vttp.csf.mp2.backend.utility.UserUtility;
import vttp.csf.mp2.backend.utility.Utils;

@RestController
@RequestMapping(path = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  @Autowired
  private UserService userSvc;

  @Autowired
  private UserUtility userUtils;

  @Autowired
  private ApplicationMetricsService appMetricsSvc;

  @PostMapping(path = "/register")
  public ResponseEntity<String> registerUser(@RequestBody String registrationPayload) {

    User newUser = userUtils.parseRegistrationPayload(registrationPayload);

    try {

      userSvc.registerUser(newUser);
      appMetricsSvc.incrementRegisterMetric();

      return ResponseEntity
          .status(HttpStatus.CREATED) // 201 CREATED
          .body(Utils.returnMessageInJson(Messages.SUCCESS_USER_REGISTRATION).toString());
    }
    catch (UserException e) {

      return ResponseEntity
          .status(HttpStatus.CONFLICT) // 409 CREATED
          .body(Utils.returnMessageInJson(e.getMessage()).toString());
    }
  }

  @PostMapping(path = "/login")
  public ResponseEntity<String> loginUser(@RequestBody String loginPayload) {

    LoginDetails login = userUtils.parseLoginPayload(loginPayload);

    try {

      JsonObject response = userSvc.loginUser(login);
      appMetricsSvc.incrementLoginMetric();

      String name = response.getString("name");
      return ResponseEntity
          .status(HttpStatus.OK) // 200 OK
          .body(Utils.returnMessageWithResponseInJson(Messages.SUCCESS_USER_LOGIN.formatted(name), response).toString());
    } 
    catch (UserException e) {

      return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED) // 401 UNAUTHORIZED
          .body(Utils.returnMessageInJson(e.getMessage()).toString());
    }
  }

  @GetMapping(path = "/confirm/{userID}")
  public ResponseEntity<String> confirmUserGet(@PathVariable String userID) {

    if (!userSvc.isUnconfirmedUserID(userID)) {

      return ResponseEntity
          .status(HttpStatus.NOT_FOUND) // 404 NOT FOUND
          .body(Utils.returnMessageInJson(Messages.FAILURE_CONFIRMATION_LINK_INVALID).toString());
    }

    return ResponseEntity
        .status(HttpStatus.OK) // 200 OK
        .body(Utils.returnMessageInJson(Messages.SUCCESS_CONFIRMATION_LINK_VALID).toString());
  }

  @PutMapping(path = "/confirm/{userID}")
  public ResponseEntity<String> confirmUserPut(@PathVariable String userID, @RequestBody String confirmationPayload) {

    String confirmationCode = userUtils.parseConfirmationPayload(confirmationPayload);

    if (!userSvc.isCorrectConfirmationCode(userID, confirmationCode)) {

      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST) // 400 BAD REQUEST
          .body(Utils.returnMessageInJson(Messages.FAILURE_INCORRECT_CONFIRMATION_CODE).toString());
    }

    userSvc.confirmUser(userID);
    appMetricsSvc.incrementConfirmMetric();

    return ResponseEntity
        .status(HttpStatus.OK) // 200 OK
        .body(Utils.returnMessageInJson(Messages.SUCCESS_USER_CONFIRMATION).toString());
  }

  @DeleteMapping(path = "/delete/{userID}")
  public ResponseEntity<String> deleteUser(@PathVariable String userID) {

    userSvc.deleteUser(userID);
    appMetricsSvc.incrementDeleteMetric();

    return ResponseEntity
        .status(HttpStatus.OK) // 200 OK
        .body(Utils.returnMessageInJson(Messages.SUCCESS_USER_DELETION).toString());
  }

  @GetMapping(path = "/profile/{userID}")
  public ResponseEntity<String> retrieveUserProfile(@PathVariable String userID) {

    try {

      JsonObject response = userSvc.retrieveUserProfile(userID);
      appMetricsSvc.incrementProfileAccessMetric();

      return ResponseEntity
      .status(HttpStatus.OK) // 200 OK
      .body(Utils.returnResponseInJson(response).toString());
    }
    catch (UserException e) {

      return ResponseEntity
      .status(HttpStatus.NOT_FOUND) // 404 NOT FOUND
      .body(Utils.returnMessageInJson(e.getMessage()).toString());
    }
  }
}
