package cz.klatismrk.market_data_producer.kafka;

import cz.klatismrk.market_data_producer.model.DTOs.TickerPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaSender {

  private final KafkaTemplate<String, TickerPriceDTO> kafkaTemplate;

  public KafkaSender(KafkaTemplate<String, TickerPriceDTO> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(String topic, TickerPriceDTO tickerPriceDTO) {
    log.debug("Sending message to Kafka topic {}: {}", topic, tickerPriceDTO);
    kafkaTemplate.send(topic, tickerPriceDTO)
        .thenAccept(result -> log.debug("Message delivered to Kafka topic {}: {}", topic, tickerPriceDTO))
        .exceptionally(ex -> {
          log.info("Failed to deliver message to Kafka topic {}: {}", topic, tickerPriceDTO, ex);;
          return null;
        });
  }
}
