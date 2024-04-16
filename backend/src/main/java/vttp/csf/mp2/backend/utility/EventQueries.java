package vttp.csf.mp2.backend.utility;

public class EventQueries {

  public static final String SQL_CHECK_BOOKMARK_EXISTS = """
      SELECT COUNT(*)
        FROM `event_bookmarks`
        WHERE `user_id` = ?
        AND `event_id` = ?
      """;

  public static final String SQL_CREATE_EVENT_BOOKMARK = """
      INSERT INTO `event_bookmarks` (`user_id`, `event_id`, `event_name`, `event_start`, `event_logo`, `event_venue`, `event_country`)
        VALUES (?, ?, ?, ?, ?, ?, ?)
      """;

  public static final String SQL_RETRIEVE_EVENT_BOOKMARKS = """
      SELECT `event_id`, `event_name`, `event_start`, `event_logo`, `event_venue`, `event_country`
        FROM `event_bookmarks`
        WHERE `user_id` = ?
      """;

  public static final String SQL_REMOVE_EVENT_BOOKMARK = """
      DELETE FROM `event_bookmarks`
        WHERE `user_id` = ?
        AND `event_id` = ?
      """;

  public static final String SQL_RETRIEVE_EVENT_BOOKMARK_COUNT = """
      SELECT COUNT(*)
        FROM `event_bookmarks`
        WHERE `event_id` = ?
      """;

  public static final String SQL_CHECK_REGISTRATION_EXISTS = """
      SELECT COUNT(*)
        FROM `event_registrations`
        WHERE `user_id` = ?
        AND `event_id` = ?
      """;

  public static final String SQL_CREATE_EVENT_REGISTRATION = """
      INSERT INTO `event_registrations` (`user_id`, `event_id`, `event_name`, `event_start`, `event_logo`, `event_venue`, `event_country`)
        VALUES (?, ?, ?, ?, ?, ?, ?)
      """;

  public static final String SQL_RETRIEVE_EVENT_REGISTRATIONS = """
      SELECT `event_id`, `event_name`, `event_start`, `event_logo`, `event_venue`, `event_country`
        FROM `event_registrations`
        WHERE `user_id` = ?
      """;
  
  public static final String SQL_REMOVE_EVENT_REGISTRATION = """
      DELETE FROM `event_registrations`
        WHERE `user_id` = ?
        AND `event_id` = ?
      """;
}