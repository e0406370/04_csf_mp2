package vttp.csf.mp2.backend.utility;

public class UserQueries {

  public static final String SQL_REGISTER_USER = """
      INSERT INTO `users` (`user_id`, `user_name`, `user_email`, `user_dob`, `user_username`, `user_password`)
        VALUES (?, ?, ?, ?, ?, ?)
      """;

  public static final String SQL_LOGIN_USER = """
      INSERT INTO `user_login` (`user_id`)
        VALUES ((SELECT `user_id` FROM `users` WHERE `user_username` = ?))
      ON DUPLICATE KEY UPDATE `last_login_date` = CURRENT_TIMESTAMP
      """;

  public static final String SQL_CHECK_USER_EXISTS = """
      SELECT COUNT(*)
        FROM `users`
        WHERE `user_id` = ?
      """;

  public static final String SQL_CHECK_EMAIL_EXISTS = """
      SELECT COUNT(*)
        FROM `users`
        WHERE `user_email` = ?
      """;

  public static final String SQL_CHECK_USERNAME_EXISTS = """
      SELECT COUNT(*)
        FROM `users`
        WHERE `user_username` = ?
      """;

  public static final String SQL_CHECK_CONFIRMATION_STATUS_BY_USERNAME = """
      SELECT `is_confirmed`
        FROM `users`
        WHERE `user_username` = ?
      """;

  public static final String SQL_CHECK_CONFIRMATION_STATUS_BY_USER_ID = """
      SELECT `is_confirmed`
        FROM `users`
        WHERE `user_id` = ?
      """;

  public static final String SQL_RETRIEVE_PASSWORD_BY_USERNAME = """
      SELECT `user_password`
        FROM `users`
        WHERE `user_username` = ?
      """;

  public static final String SQL_RETRIEVE_USER_BY_USERNAME = """
      SELECT `user_id`, `user_name`
        FROM `users`
        WHERE `user_username` = ?
      """;

  public static final String SQL_RETRIEVE_PROFILE_BY_USER_ID = """
      SELECT `user_name`, `user_email`, `user_dob`, `created_date`
        FROM `users`
        WHERE `user_id` = ?
      """;

  public static final String SQL_CONFIRM_USER = """
      UPDATE `users`
        SET `is_confirmed` = TRUE
        WHERE `user_id` = ?
      """;

  public static final String SQL_DELETE_USER = """
      DELETE FROM `users`
        WHERE `user_id` = ?
      """;
}
