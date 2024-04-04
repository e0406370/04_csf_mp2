package vttp.csf.mp2.backend.utility;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TokenUtility {

  private SecureRandom rnd = new SecureRandom();

  // generate a random digit from 0 to 9
  private String generateRandomDigit() {

    int num = rnd.nextInt(10);

    return String.valueOf(num);
  }

  // generate a random letter from A to Z
  private String generateRandomLetter() {

    char abc = (char) (rnd.nextInt(26) + 'A');

    return String.valueOf(abc);
  }

  // generate a 6-character string with 3 digits (0 ~ 9) and 3 letters (A ~ Z) with random permutation
  public String generateConfirmationToken() {

    List<String> elements = new ArrayList<>();

    for (int i = 0; i < 3; i++) {

      elements.add(generateRandomDigit());
      elements.add(generateRandomLetter());
    }

    Collections.shuffle(elements);

    return elements.toArray(String[]::new).toString();
  }

}
