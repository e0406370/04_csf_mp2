package vttp.csf.mp2.backend.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import vttp.csf.mp2.backend.utility.Constants;

@Configuration
public class MongoConfig {

  @Value("${spring.data.mongodb.database}")
  private String databaseString;

  @Value("${spring.data.mongodb.uri}")
  private String connectionString;

  private MongoClient client = null;

  @Bean
  public MongoClient mongoClient() {

    if (null == client) {

      // creates an instance of MongoClient, commmunicates with MongoDB singleton
      client = MongoClients.create(connectionString);
    }

    return client;
  }

  @Bean(Constants.BEAN_MONGO)
  public MongoTemplate mongoTemplate() {

    // creates the MongoTemplate with the respective client and the database name
    return new MongoTemplate(mongoClient(), databaseString);
  }
}
