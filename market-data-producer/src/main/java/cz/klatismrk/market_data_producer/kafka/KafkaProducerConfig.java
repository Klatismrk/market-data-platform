package cz.klatismrk.market_data_producer.kafka;

import cz.klatismrk.market_data_producer.model.DTOs.TickerPriceDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class KafkaProducerConfig {

  private final KafkaProperties kafkaProperties;

  public KafkaProducerConfig(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  @Bean
  public KafkaTemplate<String, TickerPriceDTO> ticketPriceKafkaTemplate() {
    return new KafkaTemplate<>(ticketPriceProducerFactory());
  }

  private ProducerFactory<String, TickerPriceDTO> ticketPriceProducerFactory() {
    Map<String, Object> props = kafkaProperties.buildProducerProperties();
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TicketPriceSerializer.class);

    return new DefaultKafkaProducerFactory<>(props);
  }

}
