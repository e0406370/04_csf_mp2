package vttp.csf.mp2.backend.exceptions;

public class EventException extends Exception {

  public EventException() {
  }

  public EventException(String errorMessage) {
    super(errorMessage);
  }
}