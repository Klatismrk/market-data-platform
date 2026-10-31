package cz.klatismrk.market_data_producer.binance;

import cz.klatismrk.market_data_producer.model.DTOs.TickerPriceDTO;
import cz.klatismrk.market_data_producer.model.enums.SymbolEnum;
import cz.klatismrk.market_data_producer.utils.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TickerPriceCaller {

  private final JsonMapper jsonMapper;

  private static final String BINANCE_API_URL = "https://api.binance.com/api/v3";
  private static final String TICKER_PRICE_ENDPOINT = "/ticker/price";

  private final HttpClient client = HttpClient.newBuilder()
      .connectTimeout(Duration.ofSeconds(5))
      .build();

  TickerPriceCaller(JsonMapper jsonMapper) {
    this.jsonMapper = jsonMapper;
  }

  @Scheduled(fixedRate = 1000)
  public void callTickerPrice() {
    final String url = UrlUtils.buildUrl(BINANCE_API_URL, TICKER_PRICE_ENDPOINT, getSymbolsQueryParam());
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .timeout(Duration.ofSeconds(5))
        .GET()
        .build();

    final Date apiCalled = new Date();
    final HttpResponse<String> tickerPricesRaw = getStringResponse(request);
    if (tickerPricesRaw == null) {
      log.info("Calling {} returned null response", url);
      return;
    }
    if (!HttpStatus.OK.equals(HttpStatus.valueOf(tickerPricesRaw.statusCode()))) {
      log.info("Calling {} returned invalid status code: {}", url, tickerPricesRaw.statusCode());
      return;
    }

    List<TickerPriceDTO> tickerPrices = jsonMapper.readValue(
        tickerPricesRaw.body(),
        jsonMapper.getTypeFactory().constructCollectionType(List.class, TickerPriceDTO.class)
    );
  }

  private @Nullable HttpResponse<String> getStringResponse(HttpRequest request) {
    try {
      return client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      log.info("Error while calling {}", request.uri(), e);
      return null;
    }
  }

  private static String getSymbolsQueryParam() {
    StringBuilder symbolsParam = new StringBuilder("[");
    for (SymbolEnum symbol : SymbolEnum.values()) {
      symbolsParam.append("\"").append(symbol.getSymbol()).append("\",");
    }
    symbolsParam.setLength(symbolsParam.length() - 1); // Remove the trailing ","
    symbolsParam.append("]");
    return "symbols=" + URLEncoder.encode(symbolsParam.toString(), StandardCharsets.UTF_8);
  }

}
