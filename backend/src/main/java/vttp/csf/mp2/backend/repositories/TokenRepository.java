package vttp.csf.mp2.backend.repositories;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import vttp.csf.mp2.backend.utility.Constants;

@Repository
public class TokenRepository {

  @Autowired
  @Qualifier(Constants.BEAN_REDIS)
  private RedisTemplate<String, String> redisTemplate;

  // 6 digits long from 000000 to 999999
  private String generateConfirmationToken() {

    SecureRandom rnd = new SecureRandom();
    int num = rnd.nextInt(1000000);

    return String.format("%06d", num);
  }

  public void deleteConfirmationToken(String userID) {

    ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

    valueOps.getAndDelete(userID);
  }

  public void saveConfirmationToken(String userID) {

    ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

    String confirmationToken = generateConfirmationToken();

    valueOps.setIfAbsent(userID, confirmationToken, Constants.EXPIRATION_TIME_MINS, TimeUnit.MINUTES);
  }

  public void removeUserID(String userID) {

    ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();

    zSetOps.remove("unconfirmedUsers", userID);
  }

  public void addUserID(String userID) {

    ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();

    zSetOps.add("unconfirmedUsers", userID, Constants.EXPIRATION_TIME_MINS);
  }
}
