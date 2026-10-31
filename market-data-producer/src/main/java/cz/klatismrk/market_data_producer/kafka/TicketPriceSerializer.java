package cz.klatismrk.market_data_producer.kafka;

import cz.klatismrk.market_data_producer.model.DTOs.TickerPriceDTO;
import org.apache.kafka.common.serialization.Serializer;

public class TicketPriceSerializer implements Serializer<TickerPriceDTO> {

    @Override
    public byte[] serialize(String topic, TickerPriceDTO data) {
        if (data == null) {
            return null;
        }
        return data.asJson().getBytes();
    }

}
