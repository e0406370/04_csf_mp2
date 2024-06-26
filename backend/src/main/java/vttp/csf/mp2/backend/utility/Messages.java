package vttp.csf.mp2.backend.utility;

public class Messages {

  public static final String SUCCESS_USER_REGISTRATION = """
      Successfully created account.
      Please check your inbox for a verification email to confirm your account.
      """;

  public static final String SUCCESS_USER_LOGIN = """
      Successfully logged in. Welcome %s!
      """;
      
  public static final String SUCCESS_CONFIRMATION_LINK_VALID = """
      The confirmation link for your account is valid.
      """;
      
  public static final String SUCCESS_USER_CONFIRMATION = """
      Your account has been successfully confirmed.
      You can now login into your account.
      """;
      
  public static final String SUCCESS_USER_DELETION = """
      Your account has been successfully deleted.
      Thank you for using the service.
      """;
      
  public static final String SUCCESS_EVENT_CREATE_BOOKMARK = """
      You have successfully bookmarked this event.
      """;
      
  public static final String SUCCESS_EVENT_REMOVE_BOOKMARK = """
      You have successfully removed this event from your bookmarks.
      """;

  public static final String SUCCESS_EVENT_CREATE_REGISTRATION = """
      You have successfully registered for this event.
      """;    
    
  public static final String SUCCESS_EVENT_REMOVE_REGISTRATION = """
      You have successfully removed this event from your registrations.
      """;
      
  public static final String SUCCESS_EVENT_CREATION = """
      You have successfully created a new event.
      """;

  public static final String FAILURE_EMAIL_EXISTS = """
      This email is already registered.
      Please choose a different email.
      """;
      
  public static final String FAILURE_USERNAME_EXISTS = """
      This username is already taken.
      Please choose a different username.
      """;
      
  public static final String FAILURE_USER_NOT_FOUND = """
      This user profile is not available.
      """;
      
  public static final String FAILURE_USERNAME_NOT_FOUND = """
      Username not found in database.
      """;
      
  public static final String FAILURE_INCORRECT_PASSWORD = """
      Incorrect password.
      """;
      
  public static final String FAILURE_ACCOUNT_NOT_CONFIRMED = """
      Account has not been confirmed.
      Please check your inbox for a verification email to confirm your account.
      """;
      
  public static final String FAILURE_CONFIRMATION_LINK_INVALID = """
      The confirmation link for your account is invalid or has expired.
      """;
      
  public static final String FAILURE_INCORRECT_CONFIRMATION_CODE = """
      Incorrect confirmation code.
      """;
      
  public static final String FAILURE_EVENT_NOT_FOUND = """
      This event is not available.
      """;
      
  public static final String FAILURE_EVENT_BOOKMARK_EXISTS = """
      You have already bookmarked this event.
      """;
      
  public static final String FAILURE_EVENT_REGISTRATION_EXISTS = """
      You have already registered for this event.
      """;
}
