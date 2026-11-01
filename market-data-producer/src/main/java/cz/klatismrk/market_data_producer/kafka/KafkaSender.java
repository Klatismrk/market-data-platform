package cz.klatismrk.market_data_producer.kafka;

import cz.klatismrk.market_data_producer.model.DTOs.TickerPriceDTO;
import cz.klatismrk.market_data_producer.model.wrappers.TickerPriceMessageWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaSender {

  private final KafkaTemplate<String, TickerPriceDTO> kafkaTemplate;

  private final KafkaTopicsProperties topicsProperties;

  public KafkaSender(KafkaTemplate<String, TickerPriceDTO> kafkaTemplate, KafkaTopicsProperties topicsProperties) {
    this.kafkaTemplate = kafkaTemplate;
    this.topicsProperties = topicsProperties;
  }

  public void send(TickerPriceMessageWrapper message) {
    ProducerRecord<String, TickerPriceDTO> producerRecord = message.getProducerRecord(topicsProperties);
    String topic = producerRecord.topic();
    TickerPriceDTO tickerPriceDTO = message.tickerPriceDTO();
    log.debug("Sending message to Kafka topic {}: {}", topic, tickerPriceDTO);
    kafkaTemplate.send(producerRecord)
        .thenAccept(result -> log.debug("Message delivered to Kafka topic {}: {}", topic, tickerPriceDTO))
        .exceptionally(ex -> {
          log.info("Failed to deliver message to Kafka topic {}: {}", topic, tickerPriceDTO, ex);
          return null;
        });
  }
}
