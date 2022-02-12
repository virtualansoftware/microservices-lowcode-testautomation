package io.virtualan.cucumblan.message.typeimpl;

import io.virtualan.cucumblan.message.exception.MessageNotDefinedException;
import io.virtualan.cucumblan.message.type.MessageType;
import io.virtualan.mapson.Mapson;
import io.virtualan.mapson.exception.BadInputDataException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.json.JSONObject;

public class ProtoSampleMessage implements MessageType<String, io.virtualan.proto.cucumblan.Callback> {
    private String type = "PROTOBUFF";
    private long id;
    private io.virtualan.proto.cucumblan.Callback callback;
    private String name;
    private String key;


    public ProtoSampleMessage() {
    }

    public ProtoSampleMessage(long id, String name, io.virtualan.proto.cucumblan.Callback callback) {
        this.name = name;
        this.id = id;
        this.callback = callback;
    }

    public String getType() {
        return this.type;
    }

    public List<Header> getHeaders() {
        return null;
    }

    public Long getId() {
        return this.id;
    }

    public String getKey() {
        return String.valueOf(this.id);
    }

    public io.virtualan.proto.cucumblan.Callback getMessage() {
        return this.callback;
    }

    public Object getMessageAsJson() {
        JSONObject object =new JSONObject();
        object.put("id", id);
        object.put("name", name);
        return object.toString();
    }

    public MessageType buildProducerMessage(Object messages) throws MessageNotDefinedException {
       //TODO
        return null;
    }

    public MessageType buildConsumerMessage(ConsumerRecord<String, io.virtualan.proto.cucumblan.Callback> record, String key, io.virtualan.proto.cucumblan.Callback callback) throws MessageNotDefinedException {
        this.callback = callback;
        return new ProtoSampleMessage(callback.getTwoEvent().getNumber(), callback.getTwoEvent().getAddress(), callback);
    }

    public String toString() {
        return "JSONMessage{type='" + this.type + '\'' + ", id=" + this.id + ", body=" + this.callback + '}';
    }
}
