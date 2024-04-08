package vttp.csf.mp2.backend.exceptions;

public class UserException extends Exception {

  public UserException() {
  }

  public UserException(String errorMessage) {
    super(errorMessage);
  }
}
