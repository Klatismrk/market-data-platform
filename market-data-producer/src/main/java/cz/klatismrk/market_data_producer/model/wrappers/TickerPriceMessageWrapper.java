package cz.klatismrk.market_data_producer.model.wrappers;

import cz.klatismrk.market_data_producer.kafka.KafkaTopicsProperties;
import cz.klatismrk.market_data_producer.model.DTOs.TickerPriceDTO;
import cz.klatismrk.market_data_producer.utils.TypeConvertUtils;
import org.apache.kafka.clients.producer.ProducerRecord;

public record TickerPriceMessageWrapper(TickerPriceDTO tickerPriceDTO, long apiCalledAt) {

  public static final String API_CALLED_AT_HEADER = "apiCalledAt";

  public ProducerRecord<String, TickerPriceDTO> getProducerRecord(KafkaTopicsProperties topicsProperties) {
    String topic = topicsProperties.topicFor(tickerPriceDTO.symbol());
    ProducerRecord<String, TickerPriceDTO> producerRecord = new ProducerRecord<>(topic, tickerPriceDTO.symbol().name(), tickerPriceDTO);
    producerRecord.headers().add(API_CALLED_AT_HEADER, TypeConvertUtils.longToBytes(apiCalledAt));
    return producerRecord;
  }
}
