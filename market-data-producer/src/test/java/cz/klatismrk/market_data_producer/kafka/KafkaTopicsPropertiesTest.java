package cz.klatismrk.market_data_producer.kafka;

import cz.klatismrk.market_data_producer.model.enums.SymbolEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KafkaTopicsPropertiesTest {

  @Test
  void bindsTopicForEverySymbol() {
    var binder = new Binder(new MapConfigurationPropertySource(Map.of(
        "market-data.kafka.topics.btcusdt", "a",
        "market-data.kafka.topics.btceur", "b",
        "market-data.kafka.topics.eurusdt", "c")));

    var properties = binder.bind("market-data.kafka", KafkaTopicsProperties.class).get();

    assertEquals("a", properties.topicFor(SymbolEnum.BTCUSDT));
    assertEquals("b", properties.topicFor(SymbolEnum.BTCEUR));
    assertEquals("c", properties.topicFor(SymbolEnum.EURUSDT));
  }

  @Test
  void failsWhenTopicIsMissing() {
    assertThrows(IllegalStateException.class, () -> new KafkaTopicsProperties(Map.of(SymbolEnum.BTCUSDT, "a")));
  }
}
