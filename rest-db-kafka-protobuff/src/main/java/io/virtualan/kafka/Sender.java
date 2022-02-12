package io.virtualan.kafka;

import io.virtualan.proto.cucumblan.Callback;

@org.springframework.stereotype.Component
public class Sender {

    @org.springframework.beans.factory.annotation.Autowired
    private org.springframework.kafka.core.KafkaTemplate<Long, Callback> kafkaTemplate;

    public void send(Callback request) {
        kafkaTemplate.send("spring-kafka-protobuf-demo", request);
    }
}
