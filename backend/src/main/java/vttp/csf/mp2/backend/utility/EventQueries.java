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
      SELECT `event_id`
        FROM `event_bookmarks`
        WHERE `user_id` = ?
      """;
}
