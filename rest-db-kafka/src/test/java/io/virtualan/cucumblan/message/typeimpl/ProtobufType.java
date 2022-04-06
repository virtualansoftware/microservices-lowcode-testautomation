package io.virtualan.cucumblan.message.typeimpl;


import com.jayway.jsonpath.JsonPath;
import java.util.Map.Entry;

public class ProtobufType implements
    io.virtualan.cucumblan.message.type.MessageType<String, byte[]> {

  private static final java.util.logging.Logger LOGGER = java.util.logging.Logger
      .getLogger(ProtobufType.class.getName());
  private static final com.google.protobuf.util.JsonFormat.Parser jsonParser = com.google.protobuf.util.JsonFormat
      .parser().ignoringUnknownFields();
  private static java.util.Properties protoMessageTypeMapper = new java.util.Properties();

  static {
    reload();
  }

  private String type = "ProtobufType";
  private String id;
  private String body;
  private byte[] originalBody;

  public ProtobufType() {
  }

  public ProtobufType(String id, String body, byte[] originalBody) {
    this.body = body;
    this.originalBody = originalBody;
    this.id = id;
  }

  public static void reload() {
    try {
      java.io.InputStream stream = Thread.currentThread().getContextClassLoader()
          .getResourceAsStream("message-type/proto-messagetype.properties");
      if (stream == null) {
        stream = io.virtualan.cucumblan.props.ApplicationConfiguration.class.getClassLoader()
            .getResourceAsStream("message-type/proto-messagetype.properties");
      }
      if (stream != null) {
        protoMessageTypeMapper.load(stream);
      } else {
        LOGGER.warning("unable to load proto-messagetype.properties");
      }
    } catch (Exception var1) {
      LOGGER.warning("proto-messagetype.properties not found");
    }

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

  public byte[] getMessage() {
    return this.originalBody;
  }

  //Mandatory
  public org.json.JSONObject getMessageAsJson() {
    return new org.json.JSONObject(this.body);
  }

  public io.virtualan.cucumblan.message.type.MessageType buildProducerMessage(Object messages,
      java.util.Map<String, Object> contextParam)
      throws io.virtualan.cucumblan.message.exception.MessageNotDefinedException {
    String message;
    try {
      org.json.JSONObject body;
      if (messages instanceof java.util.List) {
        message = (String) ((java.util.List) messages).stream()
            .collect(java.util.stream.Collectors.joining());
        body = new org.json.JSONObject(message);
        return serialize(body.toString(), contextParam);
      } else {

        message = io.virtualan.mapson.Mapson.buildMAPsonAsJson((java.util.Map) messages);
        body = new org.json.JSONObject(message);
        return serialize(body.toString(), contextParam);
      }
    } catch (io.virtualan.mapson.exception.BadInputDataException | io.virtualan.cucumblan.message.exception.SkipMessageException exception) {
      throw new io.virtualan.cucumblan.message.exception.MessageNotDefinedException(
          exception.getMessage());
    }
  }

  //Mandatory
  public io.virtualan.cucumblan.message.type.MessageType buildConsumerMessage(
      org.apache.kafka.clients.consumer.ConsumerRecord<String, byte[]> record,
      java.util.Map<String, Object> contextParam)
      throws io.virtualan.cucumblan.message.exception.SkipMessageException {
    try {
      return deserialize(record.value(), contextParam);
    } catch (Exception e){
      throw new io.virtualan.cucumblan.message.exception.SkipMessageException("Not a valid message");
    }
  }

  public String toString() {
    return "ProtoBuffMessageType{type='" + this.type + '\'' + ", id=" + this.id + ", body="
        + this.body + '}';
  }


    public ProtobufType serialize(String jsonbody,
        java.util.Map<String, Object> contextParam)
        throws io.virtualan.cucumblan.message.exception.SkipMessageException {
        if (protoMessageTypeMapper != null && !protoMessageTypeMapper.isEmpty()) {
            for (Entry protoMessageTypeEntry : protoMessageTypeMapper.entrySet()) {
                try {
                    if (contextParam.get("EVENT_NAME") != null
                        && protoMessageTypeEntry.getKey().toString()
                        .equalsIgnoreCase(contextParam.get("EVENT_NAME").toString())) {
                      String[] messageTypeAndJsonPath = protoMessageTypeEntry.getValue().toString().split(";");
                      if(messageTypeAndJsonPath.length ==2) {
                        byte[] body = serialize(messageTypeAndJsonPath[0],
                                jsonbody);
                        if (body != null) {
                          Object identifier = com.jayway.jsonpath.JsonPath.read(jsonbody,
                                  messageTypeAndJsonPath[1]);
                          if (identifier != null) {
                            return new ProtobufType(
                                    String.valueOf(identifier), jsonbody, body);
                          }
                        }
                      }
                    }
                } catch (Exception e) {
                    LOGGER.warning("Unable to process message :" + e.getMessage());
                }
            }
        }
        throw new io.virtualan.cucumblan.message.exception.SkipMessageException(
            "Unable to build the message");
    }

  public ProtobufType deserialize(byte[] body,
      java.util.Map<String, Object> contextParam)
      throws io.virtualan.cucumblan.message.exception.SkipMessageException {
    if (protoMessageTypeMapper != null && !protoMessageTypeMapper.isEmpty()) {

      for (Entry protoMessageTypeEntry : protoMessageTypeMapper.entrySet()) {
        try {
          if (contextParam.get("EVENT_NAME") != null
              && protoMessageTypeEntry.getKey().toString()
              .equalsIgnoreCase(contextParam.get("EVENT_NAME").toString())) {
            String[] messageTypeAndJsonPath = protoMessageTypeEntry.getValue().toString().split("(?<!\\\\);");
            if(messageTypeAndJsonPath.length ==2) {
              String bodyJson = deserialize(
                      messageTypeAndJsonPath[0], body);
              if (bodyJson != null) {
                String identifier = buildkey(bodyJson, messageTypeAndJsonPath[1]);
                if (identifier != null) {
                  return new ProtobufType(
                          String.valueOf(identifier),bodyJson, body);
                }
              }
            }
          }
        } catch (Exception e) {
            LOGGER.warning("Unable to process message :" + e.getMessage());
        }
      }
    }
    throw new io.virtualan.cucumblan.message.exception.SkipMessageException(
        "Unable to find the message");
  }

  public byte[] serialize(String classname, String messages)
      throws ClassNotFoundException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.io.IOException {
    Class clazz = Class.forName(classname);
    java.lang.reflect.Method builderGetter = clazz.getDeclaredMethod("newBuilder");
    com.google.protobuf.GeneratedMessageV3.Builder builder = (com.google.protobuf.GeneratedMessageV3.Builder) builderGetter
        .invoke(null);
    jsonParser.merge(new java.io.StringReader(messages), builder);
    return builder.build().toByteArray();
  }

  public String deserialize(String classname, byte[] payload)
      throws com.google.protobuf.InvalidProtocolBufferException {
    try {
      com.google.gson.Gson g = new com.google.gson.Gson();
      Class clazz = Class.forName(classname);
      java.lang.reflect.Method builderGetter = clazz.getDeclaredMethod("newBuilder");
      com.google.protobuf.GeneratedMessageV3.Builder builder = (com.google.protobuf.GeneratedMessageV3.Builder) builderGetter
          .invoke(null);
      builder.mergeFrom(payload);
      String body = g.toJson(builder);
      return body;
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e;
    } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
      throw new RuntimeException("Error parsing JSON message", e);
    } catch (java.io.IOException | ClassNotFoundException e) {
      throw new RuntimeException("Error creating read stream for JSON message", e);
    }
  }

  private String buildkey(String body, String paths){
    StringBuilder key = new StringBuilder();
    for(String path : paths.split("(?<!\\\\),")) {
      String pathId = path.replace("\\\\,",",");
      Object identifier = JsonPath.read(body, pathId);
      if(identifier != null) {
        if (key.length() != 0) {
          key.append("_");
        }
        key.append(identifier.toString());
      }
    }
    return key.toString();
  }

}
