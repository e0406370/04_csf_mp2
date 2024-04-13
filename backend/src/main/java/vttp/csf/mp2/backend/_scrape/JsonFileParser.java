package vttp.csf.mp2.backend._scrape;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class JsonFileParser {

  public static void main(String[] args) {
    
    parseJsonFiles();
  }

  public static void parseJsonFiles() {

    String txtFile = new File("src/main/java/vttp/csf/mp2/backend/_scrape/json/json_id.txt").getAbsolutePath();
    Set<String> finalSet = new HashSet<>();

    try (FileWriter writer = new FileWriter(txtFile, false)) {

      for (int parseCounter = 1; parseCounter <= 20; parseCounter++) {

        String jsonFile = new File(String.format("src/main/java/vttp/csf/mp2/backend/_scrape/json/events%d.json", parseCounter)).getAbsolutePath();
        Set<String> eventSet = new HashSet<>();

        File eventsJsonFile = new File(jsonFile);
        try (InputStream is = new FileInputStream(eventsJsonFile)) {

          JsonReader jsonReader = Json.createReader(is);
          JsonObject jsonObject = jsonReader.readObject();

          JsonArray events = jsonObject.getJsonArray("events");
          eventSet = events.stream()
              .map(jv -> jv.asJsonObject())
              .map(jo -> {
                String eventID = jo.getString("id");
                return eventID;
              })
              .collect(Collectors.toSet());

          int currentEventsCount = finalSet.size();
          finalSet.addAll(eventSet);

          System.out.printf("(File %d) Number of events available: %d (Added: %d) \n", parseCounter, eventSet.size(), currentEventsCount);
        } 
        catch (Exception e) {
          e.printStackTrace();
        }
      }
      
      for (String eventID : finalSet) {
        writer.write(eventID);
        writer.write("\n");
      }

    } 
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (Exception e) {
      System.err.println("Unknown error occurred. Please try again later.");
    }

    System.out.printf("Total number of events available: %d\n", finalSet.size());
  }
}
