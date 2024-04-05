package vttp.csf.mp2.backend.repositories;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import vttp.csf.mp2.backend.utility.Constants;
import vttp.csf.mp2.backend.utility.TokenUtility;

@Repository
public class TokenRepository {

  @Autowired
  @Qualifier(Constants.BEAN_REDIS)
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private TokenUtility tokenUtils;

  public void deleteConfirmationToken(String userID) {

    redisTemplate.delete(userID);
  }

  public void saveConfirmationToken(String userID) {

    ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

    String confirmationToken = tokenUtils.generateConfirmationToken();

    valueOps.setIfAbsent(userID, confirmationToken, Constants.EXPIRATION_TIME_MINS, TimeUnit.MINUTES);
  }

  public void removeUserID(String userID) {

    ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();

    zSetOps.remove(Constants.UNCONFIRMED_USERS_ZSET, userID);
  }

  public void addUserID(String userID) {

    ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();

    zSetOps.add(Constants.UNCONFIRMED_USERS_ZSET, userID, Constants.EXPIRATION_TIME_MINS);
  }

  public boolean isUnconfirmedUserID(String userID) {

    return redisTemplate.hasKey(userID);
  }

  public boolean isCorrectConfirmationCode(String userID, String confirmationCode) {

    ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

    return valueOps.get(userID).toString().equals(confirmationCode);
  }
}
