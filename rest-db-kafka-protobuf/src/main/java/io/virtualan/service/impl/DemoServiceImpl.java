package io.virtualan.service.impl;

import io.virtualan.proto.cucumblan.Callback;
import io.virtualan.proto.cucumblan.KafkaEventOne;
import io.virtualan.proto.cucumblan.KafkaEventTwo;
import io.virtualan.service.DemoService;

@org.springframework.stereotype.Service
public class DemoServiceImpl implements DemoService {

    @org.springframework.beans.factory.annotation.Autowired
    private org.springframework.kafka.core.KafkaTemplate<Long, Callback> kafkaTemplate;

    public String run(int id, String name) {

        Callback request = Callback.newBuilder()
                .setTwoEvent(KafkaEventTwo.newBuilder().setNumber(id).setAddress(name).build())
                .build();
            kafkaTemplate.send("virtualan.proto.buff.input.demo", request);

        return "Success";
    }
}
