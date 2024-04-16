package vttp.csf.mp2.backend.utility;

public class EventQueries {

  public static final String SQL_CHECK_BOOKMARK_EXISTS = """
    SELECT COUNT(*)
      FROM `event_bookmarks`
      WHERE `event_id` = ?
      AND `user_id` = ?
    """;
  
  public static final String SQL_CREATE_EVENT_BOOKMARK = """
    INSERT INTO `event_bookmarks` (`event_id`, `user_id`)
      VALUES (?, ?)
    """;    
}
