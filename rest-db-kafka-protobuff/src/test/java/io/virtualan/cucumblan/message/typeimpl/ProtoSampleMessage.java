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
    private io.virtualan.proto.cucumblan.Callback body;
    private String name;
    private String key;


    public ProtoSampleMessage() {
    }

    public ProtoSampleMessage(long id, String name) {
        this.name = name;
        this.id = id;
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
        return this.key;
    }

    public io.virtualan.proto.cucumblan.Callback getMessage() {
        return this.body;
    }

    public Object getMessageAsJson() {
        JSONObject object =new JSONObject();
        object.put("id", id);
        object.put("name", name);
        return object.toString();
    }

    public MessageType buildProducerMessage(Object messages) throws MessageNotDefinedException {
        String message;
        JSONObject body;
        if (messages instanceof List) {
            message = (String)((List)messages).stream().collect(Collectors.joining());
            body = new JSONObject(message);
            return new JSONMessage(body.getInt("id"), message);
        } else {
            message = null;

            try {
                message = Mapson.buildMAPsonAsJson((Map)messages);
                body = new JSONObject(message);
                return new JSONMessage(body.getInt("id"), message);
            } catch (BadInputDataException var4) {
                throw new MessageNotDefinedException(var4.getMessage());
            }
        }
    }

    public MessageType buildConsumerMessage(ConsumerRecord<String, io.virtualan.proto.cucumblan.Callback> record, String key, io.virtualan.proto.cucumblan.Callback callback) throws MessageNotDefinedException {
         return new ProtoSampleMessage(callback.getTwoEvent().getNumber(), callback.getTwoEvent().getAddress());
    }

    public String toString() {
        return "JSONMessage{type='" + this.type + '\'' + ", id=" + this.id + ", body=" + this.body + '}';
    }
}
