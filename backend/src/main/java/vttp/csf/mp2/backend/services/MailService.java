package vttp.csf.mp2.backend.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vttp.csf.mp2.backend.models.User;
import vttp.csf.mp2.backend.utility.UserUtility;

@Service
public class MailService {

  @Autowired
  private UserUtility userUtils;

  @Value("${mailgun.api.key}")
  private String API_KEY;

  @Value("${mailgun.api.url}")
  private String API_URL;

  @Value("${mailgun.sender.email}")
  private String SENDER_EMAIL;

  @Value("${mailgun.template.confirmation}")
  private String TEMPLATE_CONFIRMATION;

  private RestTemplate restTemplate = new RestTemplate();

  private Logger logger = Logger.getLogger(MailService.class.getName());

  public void sendConfirmationEmail(User newUser, String confirmationToken) {

    String requestUrl = UriComponentsBuilder
        .fromUriString(API_URL)
        .path("messages")
        .toUriString();

    String variables = """
        {
          "confirmationCode": "%s",
          "confirmationLink": "http://localhost:4200/#/user/confirm/%s"
        }
        """.formatted(confirmationToken, newUser.userID());

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("from", SENDER_EMAIL);
    body.add("to", newUser.email());
    body.add("subject", "Welcome %s!".formatted(newUser.name()));
    body.add("template", TEMPLATE_CONFIRMATION);
    body.add("h:X-Mailgun-Variables", variables);

    RequestEntity<MultiValueMap<String, String>> request = RequestEntity
        .post(requestUrl)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(body);

    restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("api", API_KEY));

    try {
      ResponseEntity<String> response = restTemplate.exchange(request, String.class);

      logger.info("Confirmation email sent to %s".formatted(newUser.email()));
      logger.info("Email ID: %s".formatted(userUtils.parseEmailPayload(response.getBody())));
    } 
    catch (Exception e) {
      logger.severe("An error occurred while sending a confirmation email to %s: %s".formatted(newUser.email(), e.getMessage()));
    }
  }
}
