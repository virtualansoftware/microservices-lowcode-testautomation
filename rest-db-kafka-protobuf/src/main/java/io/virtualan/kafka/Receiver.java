package io.virtualan.kafka;

import io.virtualan.proto.cucumblan.Callback;

@org.springframework.stereotype.Component
public class Receiver {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(io.virtualan.kafka.Receiver.class);

    @org.springframework.kafka.annotation.KafkaListener(topics = {"virtualan.proto.buff.demo"})
    public void listen(org.apache.kafka.clients.consumer.ConsumerRecord<?, Callback> record) {
        Callback callback = record.value();
        logger.warn("message: {}", callback);
        if (callback == null) return;

        switch (callback.getEventTypeCase()) {
            case ONE_EVENT:
                logger.warn("one event: {}", callback.getOneEvent());
                break;
            case TWO_EVENT:
                logger.warn("two event: {}", callback.getOneEvent());
                break;
            default:
                logger.warn("default value...");
                break;
        }
    }
}
