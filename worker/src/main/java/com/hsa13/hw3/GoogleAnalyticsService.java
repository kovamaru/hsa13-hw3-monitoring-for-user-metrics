package com.hsa13.hw3;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.annotation.Client;
import lombok.extern.slf4j.Slf4j;

@Client("https://www.google-analytics.com/mp/collect?measurement_id=${google.analytics.measurement-id}&api_secret=${google.analytics.api-secret}")
interface GoogleAnalyticsClient {
  @Post
  HttpResponse<String> sendEvent(@Body Map<String, Object> body);
}

@Singleton
@Slf4j
public class GoogleAnalyticsService {

  @Value("${google.analytics.client-id}")
  private String clientId;

  private final GoogleAnalyticsClient googleAnalyticsClient;

  public GoogleAnalyticsService(GoogleAnalyticsClient googleAnalyticsClient) {
    this.googleAnalyticsClient = googleAnalyticsClient;
  }

  public void sendEvent(double exchangeRate) {
    Map<String, Object> eventParams = new HashMap<>();
    eventParams.put("name", "exchange_rate_update");
    eventParams.put("params", Map.of("uah_usd_rate", exchangeRate));

    Map<String, Object> payload = new HashMap<>();
    payload.put("client_id", clientId);
    payload.put("events", new Map[]{eventParams});

    HttpResponse<String> response = googleAnalyticsClient.sendEvent(payload);

    log.info("GA Response: {} {}", response.getStatus(), response.getBody());
  }
}
