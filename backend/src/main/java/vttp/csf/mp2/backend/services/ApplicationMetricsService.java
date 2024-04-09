package vttp.csf.mp2.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class ApplicationMetricsService {

  private final Counter registerCounter;
  private final Counter loginCounter;
  private final Counter confirmCounter;
  private final Counter deleteCounter;
  private final Counter profileAccessCounter;

  @Autowired
  public ApplicationMetricsService(MeterRegistry registry) {

    registerCounter = Counter.builder("registration_counter")
        .description("Tracks the number of attempted user registrations on the site")
        .register(registry);

    loginCounter = Counter.builder("login_counter")
        .description("Tracks the number of successful user logins on the site")
        .register(registry);

    confirmCounter = Counter.builder("confirm_counter")
        .description("Tracks the number of confirmed user registrations on the site")
        .register(registry);

    deleteCounter = Counter.builder("delete_counter")
        .description("Tracks the number of accounts successfully deleted from the site")
        .register(registry);

    profileAccessCounter = Counter.builder("profile_access_counter")
        .description("Tracks the number of times a profile is accessed on the site")
        .register(registry);
  }

  public void incrementRegisterMetric() {
    registerCounter.increment();
  }

  public void incrementLoginMetric() {
    loginCounter.increment();
  }

  public void incrementConfirmMetric() {
    confirmCounter.increment();
  }

  public void incrementDeleteMetric() {
    deleteCounter.increment();
  }

  public void incrementProfileAccessMetric() {
    profileAccessCounter.increment();
  }
}
