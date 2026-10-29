package cz.klatismrk.market_data_producer.model.DTOs;

import cz.klatismrk.market_data_producer.model.enums.SymbolEnum;

public record TickerPriceDTO(SymbolEnum symbol, Double price) {

}
