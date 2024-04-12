package vttp.csf.mp2.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(BackendApplication.class, args);
	}

	// @Autowired
	// private WebScraper scraper;

	// @Autowired
	// private EventbriteService eventbriteSvc;

	// @Override
	// public void run(String... args) throws Exception {

	// 	// scraper.scrapeJsonScript(); // note: run directly via psvm

	// 	// eventbriteSvc.retrieveEventbriteDataInBulk();		
	// }
}
