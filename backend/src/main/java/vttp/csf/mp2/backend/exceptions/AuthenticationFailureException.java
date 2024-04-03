package vttp.csf.mp2.backend.exceptions;

public class AuthenticationFailureException extends Exception {

  public AuthenticationFailureException() {
  }

  public AuthenticationFailureException(String errorMessage) {
    super(errorMessage);
  }
}
