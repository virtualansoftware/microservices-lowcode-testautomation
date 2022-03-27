package io.virtualan.cucumblan.message.typeimpl;


public class JSONMessageType implements io.virtualan.cucumblan.message.type.MessageType<String, String> {
    private String type = "JSONMessageType";
    private String id;
    private String body;

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(JSONMessageType.class.getName());

    private static java.util.Properties jsonMessageTypeMapper = new java.util.Properties();

    static {
        reload();
    }

    public static void reload() {
        try {
            java.io.InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("json-messagetype.properties");
            if (stream == null) {
                stream = io.virtualan.cucumblan.props.ApplicationConfiguration.class.getClassLoader().getResourceAsStream("json-messagetype.properties");
            }
            if (stream != null) {
                jsonMessageTypeMapper.load(stream);
            } else {
                LOGGER.warning("unable to load json-messagetype.properties");
            }
        } catch (Exception var1) {
            LOGGER.warning("json-messagetype.properties not found");
        }

    }

    public JSONMessageType() {
    }

    public JSONMessageType(String id, String body) {
        this.body = body;
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public java.util.List<org.apache.kafka.common.header.Header> getHeaders() {
        return null;
    }

    //Mandatory
    public String getId() {
        return this.id;
    }

    public String getKey() {
        return this.id;
    }

    public String getMessage() {
        return this.body;
    }

    //Mandatory
    public org.json.JSONObject getMessageAsJson() {
        return new org.json.JSONObject(this.body);
    }

    public io.virtualan.cucumblan.message.type.MessageType buildProducerMessage(Object messages) throws io.virtualan.cucumblan.message.exception.MessageNotDefinedException {
        String message;
        org.json.JSONObject body;
        if (messages instanceof java.util.List) {
            message = (String) ((java.util.List) messages).stream().collect(java.util.stream.Collectors.joining());
            body = new org.json.JSONObject(message);
            return buildMessageType(body.toString());
        } else {
            try {
                message = io.virtualan.mapson.Mapson.buildMAPsonAsJson((java.util.Map) messages);
                body = new org.json.JSONObject(message);
                return buildMessageType(body.toString());
            } catch (io.virtualan.mapson.exception.BadInputDataException exception) {
                throw new io.virtualan.cucumblan.message.exception.MessageNotDefinedException(exception.getMessage());
            }
        }
    }

    //Mandatory
    public io.virtualan.cucumblan.message.type.MessageType buildConsumerMessage(org.apache.kafka.clients.consumer.ConsumerRecord<String, String> record, String key, String body) throws io.virtualan.cucumblan.message.exception.MessageNotDefinedException {
        return buildMessageType(body);
    }

    public String toString() {
        return "JSONMessageType{type='" + this.type + '\'' + ", id=" + this.id + ", body=" + this.body + '}';
    }

    public JSONMessageType buildMessageType(String body) throws io.virtualan.cucumblan.message.exception.MessageNotDefinedException {
         JSONMessageType messageType = jsonMessageTypeMapper.entrySet()
                .stream().filter( x -> {
                                        String identifier = com.jayway.jsonpath.JsonPath.read(body, x.getValue().toString());
                                if(identifier != null) return true;
                                else return false;
                }).map( x -> new JSONMessageType(com.jayway.jsonpath.JsonPath.read(body, x.getValue().toString()), body)).collect(java.util.stream.Collectors.toList()).get(0);
         if(messageType != null) return messageType;
        throw new io.virtualan.cucumblan.message.exception.MessageNotDefinedException(body);
    }
}
