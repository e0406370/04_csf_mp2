package vttp.csf.mp2.backend.services;

import java.util.Set;
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

  private Logger logger = Logger.getLogger(TokenService.class.getName());

  @Autowired
  @Qualifier(Constants.BEAN_REDIS)
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private TokenRepository tokenRepo;

  @Autowired
  private UserRepository userRepo;

  @Scheduled(fixedDelay = 60000)
  public void removeExpiredTokens() {

    logger.info("Checking for expired tokens...");

    long currentTime = System.currentTimeMillis();
    Set<String> unconfirmedUsers = redisTemplate.opsForZSet().rangeByScore("unconfirmedUsers", 0, currentTime + Constants.EXPIRATION_TIME_MINS);

    for (String userID : unconfirmedUsers) {

      if (!redisTemplate.hasKey(userID)) {

        tokenRepo.removeUserID(userID);
        logger.info("Deleted Redis record in `unconfirmedUsers` sorted set with userID: %s".formatted(userID));

        userRepo.deleteUserByUserID(userID);
        logger.info("Deleted MySQL record in `users` table with userID: %s".formatted(userID));
      }
    } 
  }
}
