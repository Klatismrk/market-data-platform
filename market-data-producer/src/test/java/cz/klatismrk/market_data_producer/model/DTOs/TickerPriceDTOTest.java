package cz.klatismrk.market_data_producer.model.DTOs;

import cz.klatismrk.market_data_producer.model.enums.SymbolEnum;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TickerPriceDTOTest {

  private final JsonMapper jsonMapper = JsonMapper.builder().build();

  private static final String SYMBOL_FIELD = "symbol";
  private static final String PRICE_FIELD = "price";

  @Test
  void asJsonSerializesRecordFields() {
    final SymbolEnum symbolEnum = SymbolEnum.BTCUSDT;
    final double price = 123.45;
    final TickerPriceDTO tickerPrice = new TickerPriceDTO(symbolEnum, price);

    var json = jsonMapper.readTree(tickerPrice.asJson());

    assertEquals(2, json.size());
    assertEquals(symbolEnum.getSymbol(), json.get(SYMBOL_FIELD).asString());
    assertTrue(json.get(PRICE_FIELD).isNumber());
    assertEquals(price, json.get(PRICE_FIELD).asDouble());
    assertEquals(tickerPrice, jsonMapper.readValue(tickerPrice.asJson(), TickerPriceDTO.class));
  }

  @Test
  void asJsonSerializesNullFields() {
    final TickerPriceDTO tickerPrice = new TickerPriceDTO(null, null);
    var json = jsonMapper.readTree(tickerPrice.asJson());

    assertEquals(2, json.size());
    assertTrue(json.get(SYMBOL_FIELD).isNull());
    assertTrue(json.get(PRICE_FIELD).isNull());
  }
}
