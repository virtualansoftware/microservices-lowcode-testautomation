package io.virtualan.kafka;

import io.virtualan.proto.cucumblan.Callback;

public class CallbackSerializer extends io.virtualan.kafka.Adapter implements org.apache.kafka.common.serialization.Serializer<Callback> {

    @Override
    public byte[] serialize(final String topic, final Callback data) {
        return data.toByteArray();
    }
}
