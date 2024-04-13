package vttp.csf.mp2.backend._scrape;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import jakarta.json.JsonArray;

import vttp.csf.mp2.backend.utility.Utils;

public class WebScraper {

  // private static final String[] countriesList = new String[] {
  //   "australia",
  //   "brunei",
  //   "denmark",
  //   "indonesia",
  //   "japan",
  //   "malaysia",
  //   "myanmar",
  //   "new-zealand",
  //   "singapore",
  //   "switzerland"
  //   "south-korea",
  //   "taiwan",
  //   "thailand",
  //   "united-kingdom",
  //   "united-arab-emirates",
  // };
  // 15 countries

  public static void main(String[] args) {

    String eventUrl = "https://www.eventbrite.sg/d/singapore/all-events/?page=%d";

    scrapeJsonScript(eventUrl);
  }

  // expected total -> (20 links per page) * (maximum of 50 pages) = (1000 links)
  // can perform 2000 API calls per hour (1 call per event ID) => retrieve 2000 events (documents) per hour
  public static void scrapeJsonScript(String eventUrl) {

    String txtFile = new File("src/main/java/vttp/csf/mp2/backend/_scrape/scrape_id.txt").getAbsolutePath();
    List<String> eventLinks = new LinkedList<>();

    try (FileWriter writer = new FileWriter(txtFile, false)) {

      for (int scrapeCounter = 1; scrapeCounter <= 50; scrapeCounter++) {

        Document doc = Jsoup.connect(eventUrl.formatted(scrapeCounter)).get();

        Element jsonScript = doc.select("script[type=application/ld+json]").first();

        if (jsonScript != null) {

          String jsonData = jsonScript.html();
          // System.out.println(jsonData);

          JsonArray events = Utils.returnPayloadInJson(jsonData).getJsonArray("itemListElement");
          eventLinks = events.stream()
              .map(jv -> jv.asJsonObject())
              .map(jo -> {
                String eventLink = jo.getJsonObject("item").getString("url");
                return eventLink;
              })
              .collect(Collectors.toList());

          eventLinks.forEach(x -> System.out.println(x));
          System.out.println("Scraped page (%d / 50)".formatted(scrapeCounter));

          for (String eventID : eventLinks) {
            writer.write(eventID);
            writer.write("\n");
          }
        } 
        else {
          System.out.println("No <script> element with type 'application/ld+json' found.");
        }
      }
    } 
    catch (IOException e) {
      e.printStackTrace();
    } 
    catch (Exception e) {
      System.err.println("Unknown error occurred. Please try again later.");
    }
  }
}