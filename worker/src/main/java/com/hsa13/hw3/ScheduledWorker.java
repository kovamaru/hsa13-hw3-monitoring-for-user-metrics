package com.hsa13.hw3;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class ScheduledWorker {

  private final ExchangeRateService exchangeRateService;
  private final GoogleAnalyticsService googleAnalyticsService;

  public ScheduledWorker(ExchangeRateService exchangeRateService, GoogleAnalyticsService googleAnalyticsService) {
    this.exchangeRateService = exchangeRateService;
    this.googleAnalyticsService = googleAnalyticsService;
  }

  @Scheduled(fixedRate = "1h")
  public void pushExchangeRateToGA() {
    double rate = exchangeRateService.getExchangeRate();
    googleAnalyticsService.sendEvent(rate);

    log.info("Sent exchange rate to GA4: {}", rate);
  }
}
