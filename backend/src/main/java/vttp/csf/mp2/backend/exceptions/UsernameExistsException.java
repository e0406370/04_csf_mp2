package vttp.csf.mp2.backend.exceptions;

public class UsernameExistsException extends Exception {

  public UsernameExistsException() {
  }

  public UsernameExistsException(String errorMessage) {
    super(errorMessage);
  }
}
