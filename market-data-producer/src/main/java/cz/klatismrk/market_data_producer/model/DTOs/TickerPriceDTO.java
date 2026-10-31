package cz.klatismrk.market_data_producer.model.DTOs;

import cz.klatismrk.market_data_producer.model.enums.SymbolEnum;
import tools.jackson.databind.json.JsonMapper;

public record TickerPriceDTO(SymbolEnum symbol, Double price) {

  private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();

  public String asJson() {
    return JSON_MAPPER.writeValueAsString(this);
  }
}
