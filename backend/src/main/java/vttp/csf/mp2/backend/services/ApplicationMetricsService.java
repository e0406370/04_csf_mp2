package vttp.csf.mp2.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class ApplicationMetricsService {

  private final Counter registerCounter;
  private final Counter confirmCounter;
  private final Counter loginCounter;

  @Autowired
  public ApplicationMetricsService(MeterRegistry registry) {

    registerCounter = Counter.builder("registration_counter")
        .description("Number of attempted registrations on the site")
        .register(registry);

    confirmCounter = Counter.builder("confirm_counter")
        .description("Number of confirmed registrations on the site")
        .register(registry);

    loginCounter = Counter.builder("login_counter")
        .description("Number of successful logins on the site")
        .register(registry);
  }

  public void incrementRegisterMetric() {
    registerCounter.increment();
  }

  public void incrementConfirmMetric() {
    confirmCounter.increment();
  }

  public void incrementLoginMetric() {
    loginCounter.increment();
  }
}