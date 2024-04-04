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

    long currentTime = System.currentTimeMillis();
    Set<String> expiredTokens = redisTemplate.opsForZSet().rangeByScore(null, currentTime, currentTime);

    
  }

  // @Async
  // public void start() {

  //   Runnable poller = () -> {

  //     logger.info("Start polling sales queue...");

  //     while (true) {
  //       logger.info("Polling...");

  //       // wait for 10 seconds before processing this line
  //       // String value = listOpr.rightPop("sales", Duration.ofSeconds(10));

  //       if ((null == value) || ("" == value.trim())) {
  //         logger.info("No data repolling...");
  //         continue;
  //       }

  //       // process the data here
  //       logger.info(">>>>> Data polled: %s".formatted(value));
  //     }
  //   };

  //   ExecutorService threadPool = Executors.newFixedThreadPool(1);
  //   threadPool.submit(poller);
  // }

}
