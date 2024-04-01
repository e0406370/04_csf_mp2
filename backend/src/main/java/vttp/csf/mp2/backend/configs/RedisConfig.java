package vttp.csf.mp2.backend.configs;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import vttp.csf.mp2.backend.utility.Utility;

@Configuration
public class RedisConfig {

  // initialised for logging information on Redis configuration
  private Logger logger = Logger.getLogger(RedisConfig.class.getName());

  // injects essential Redis connection properties from application.properties file
  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private Integer redisPort;

  @Value("${spring.redis.username}")
  private String redisUsername;

  @Value("${spring.redis.password}")
  private String redisPassword;

  @Value("${spring.redis.database}")
  private Integer redisDatabase;

  private JedisConnectionFactory createFactory() {

    // creates an instance of RedisStandaloneConfiguration
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

    // sets configuration values from injected properties
    config.setHostName(redisHost);
    config.setPort(redisPort);
    config.setDatabase(redisDatabase);

    // sets the username and password if provided
    if (redisUsername != null && !redisUsername.isEmpty()) {
      config.setUsername(redisUsername);
    }
    if (redisPassword != null && !redisPassword.isEmpty()) {
      config.setPassword(redisPassword);
    }

    // logs critical information on various Redis configuration properties
    logger.log(Level.INFO,
        "Using Redis host: %s".formatted(redisHost));

    logger.log(Level.INFO,
        "Using Redis port: %d".formatted(redisPort));

    logger.log(Level.INFO,
        "Using Redis database: %d".formatted(redisDatabase));

    logger.log(Level.INFO,
        "Using Redis username is set: %b".formatted(redisUsername != null && !redisUsername.isEmpty()));

    logger.log(Level.INFO,
        "Using Redis password is set: %b".formatted(redisPassword != null && !redisPassword.isEmpty()));

    // creates the client and factory
    JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
    JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
    jedisFac.afterPropertiesSet();

    return jedisFac;
  }

  @Bean(Utility.BEAN_REDIS)
  public RedisTemplate<String, String> createRedisConnection() {

    // creates an instance of JedisConnectionFactory, initialised by the method written above
    JedisConnectionFactory jedisFac = this.createFactory();

    // creates the RedisTemplate with the associated connection factory 
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisFac);

    // Keys => set in UTF-8
    // Values => optional value serializer if string values are to be saved as UTF-8
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(new StringRedisSerializer());

    return template;
  }
}
