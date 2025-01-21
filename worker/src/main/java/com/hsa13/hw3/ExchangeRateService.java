package com.hsa13.hw3;

import io.micronaut.http.annotation.Get;
import jakarta.inject.Singleton;
import io.micronaut.http.client.annotation.Client;
import java.util.Map;
import io.micronaut.retry.annotation.Retryable;

@Client("https://bank.gov.ua/NBUStatService/v1/statdirectory")
interface NbuApiClient {
  @Get("/exchange?valcode=USD&json")
  Map<String, Object>[] getExchangeRate();
}

@Singleton
public class ExchangeRateService {
  private final NbuApiClient nbuApiClient;

  public ExchangeRateService(NbuApiClient nbuApiClient) {
    this.nbuApiClient = nbuApiClient;
  }

  @Retryable(attempts = "3", delay = "2s")
  public double getExchangeRate() {
    Map<String, Object>[] response = nbuApiClient.getExchangeRate();
    if (response != null && response.length > 0) {
      return Double.parseDouble(response[0].get("rate").toString());
    }
    throw new RuntimeException("Unable to fetch exchange rate from NBU API");
  }
}
