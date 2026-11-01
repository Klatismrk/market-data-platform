package cz.klatismrk.market_data_producer.utils;

import java.nio.ByteBuffer;

public class TypeConvertUtils {

  public static byte[] longToBytes(long l) {
    return ByteBuffer.allocate(Long.BYTES)
        .putLong(l)
        .array();
  }
}
