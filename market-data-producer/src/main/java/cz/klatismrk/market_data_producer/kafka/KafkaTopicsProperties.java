package cz.klatismrk.market_data_producer.kafka;

import cz.klatismrk.market_data_producer.model.enums.SymbolEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties("market-data.kafka")
public record KafkaTopicsProperties(Map<SymbolEnum, String> topics) {

  public KafkaTopicsProperties {
    Map<SymbolEnum, String> copy = new EnumMap<>(SymbolEnum.class);
    if (topics != null) {
      copy.putAll(topics);
    }

    List<SymbolEnum> missing = Arrays.stream(SymbolEnum.values())
        .filter(symbol -> copy.get(symbol) == null || copy.get(symbol).isBlank())
        .toList();
    if (!missing.isEmpty()) {
      throw new IllegalStateException("Missing Kafka topic configuration (market-data.kafka.topics.*) for: " + missing);
    }
    topics = copy;
  }

  public String topicFor(SymbolEnum symbol) {
    return topics.get(symbol);
  }
}
