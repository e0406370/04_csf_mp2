package vttp.csf.mp2.backend.utility;

public class SQLQueries {

  public static final String SQL_REGISTER_USER = """
      INSERT INTO `users` (`user_id`, `user_name`, `user_email`, `user_dob`, `user_username`, `user_password`)
        VALUES (?, ?, ?, ?, ?, ?)
      """;

  public static final String SQL_CHECK_EMAIL_EXISTS = """
      SELECT COUNT(*) FROM `users`
        WHERE email = ?
      """;

  public static final String SQL_CHECK_USERNAME_EXISTS = """
      SELECT COUNT(*) FROM `users`
        WHERE username = ?
      """;

}
