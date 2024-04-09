package vttp.csf.mp2.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import vttp.csf.mp2.backend.services.EventbriteService;

@SpringBootApplication
@EnableScheduling
public class BackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		
		SpringApplication.run(BackendApplication.class, args);
	}

	// @Autowired
	// private JsonFileParser parser;

	// @Autowired
	// private WebScraper scraper;

	@Autowired
	private EventbriteService eventbriteSvc;

	@Override
	public void run(String... args) throws Exception {

		// parser.parseJsonFiles();

		// scraper.scrapeJsonScript();

		eventbriteSvc.retrieveEventbriteDataInBulk();		
	}
}
