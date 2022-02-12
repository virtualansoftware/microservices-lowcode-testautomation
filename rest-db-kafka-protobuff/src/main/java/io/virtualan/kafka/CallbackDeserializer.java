package io.virtualan.kafka;

import io.virtualan.proto.cucumblan.Callback;

public class CallbackDeserializer  extends io.virtualan.kafka.Adapter implements org.apache.kafka.common.serialization.Deserializer<Callback> {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(io.virtualan.kafka.CallbackDeserializer.class);

    @Override
    public Callback deserialize(final String topic, byte[] data) {
        try {
            return Callback.parseFrom(data);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            logger.error("Received un-parse message exception and skip.");
            return null;
            // throw new RuntimeException("Received un-parse message " + e.getMessage(), e);
        }
    }
}
