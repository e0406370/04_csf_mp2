package vttp.csf.mp2.backend.exceptions;

public class EmailExistsException extends Exception {

  public EmailExistsException() {}

  public EmailExistsException(String errorMessage) {
    super(errorMessage);
  }
}
