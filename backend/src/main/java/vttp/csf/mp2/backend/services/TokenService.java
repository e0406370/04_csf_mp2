package vttp.csf.mp2.backend.services;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import vttp.csf.mp2.backend.repositories.TokenRepository;
import vttp.csf.mp2.backend.repositories.UserRepository;
import vttp.csf.mp2.backend.utility.Constants;

@Component
public class TokenService {

  @Autowired
  @Qualifier(Constants.BEAN_REDIS)
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private TokenRepository tokenRepo;

  @Autowired
  private UserRepository userRepo;

  private Logger logger = Logger.getLogger(TokenService.class.getName());

  @Scheduled(fixedDelay = Constants.SCHEDULED_INTERVAL_MINS, timeUnit = TimeUnit.MINUTES)
  public void monitorExpiredTokens() {

    logger.info("Checking for expired confirmation tokens...");

    long minBound = 0;
    long maxBound = System.currentTimeMillis() / (1000 * 60) + Constants.EXPIRATION_TIME_MINS;

    Set<String> unconfirmedUsers = redisTemplate.opsForZSet().rangeByScore(Constants.UNCONFIRMED_USERS_ZSET, minBound, maxBound);

    for (String userID : unconfirmedUsers) {

      if (!redisTemplate.hasKey(userID)) {
        deleteExpiredToken(userID);
      }
    }
  }

  private void deleteExpiredToken(String userID) {

    tokenRepo.removeUserID(userID);
    logger.info("Deleted Redis record in `unconfirmed_users_zset` sorted set with userID: %s".formatted(userID));

    userRepo.deleteUser(userID);
    logger.info("Deleted MySQL record in `users` table with userID: %s".formatted(userID));
  }
}
