package cz.klatismrk.market_data_producer.model.enums;

public enum SymbolEnum {

  BTCUSDT("BTCUSDT"),
  BTCEUR("BTCEUR"),
  EURUSDT("EURUSDT");

  private final String symbol;

  SymbolEnum(String symbol) {
    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }
}
