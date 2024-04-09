package vttp.csf.mp2.backend.utility;

public class Constants {

  public static final String DATE_PATTERN = "YYYY-MM-dd";
  public static final String DATE_TIME_PATTERN = "YYYY-MM-dd HH:mm:ss";

  public static final String BEAN_MONGO = "mymongo";
  public static final String BEAN_REDIS = "myredis";

  public static final String UNCONFIRMED_USERS_ZSET = "unconfirmed_users_zset";
  public static final long EXPIRATION_TIME_MINS = 30;
  public static final long SCHEDULED_INTERVAL_MINS = 5;

  public static final String DOMAIN_NAME = "http://localhost:4200";
}
