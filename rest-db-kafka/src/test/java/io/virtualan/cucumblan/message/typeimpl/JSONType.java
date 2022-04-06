package io.virtualan.cucumblan.message.typeimpl;


import com.jayway.jsonpath.JsonPath;
import java.util.Map.Entry;

public class JSONType implements
    io.virtualan.cucumblan.message.type.MessageType<String, String> {

  private static final java.util.logging.Logger LOGGER = java.util.logging.Logger
      .getLogger(JSONType.class.getName());
  private static java.util.Properties jsonMessageTypeMapper = new java.util.Properties();

  static {
    reload();
  }

  private String type = "JSONType";
  private String id;
  private String body;

  public JSONType() {
  }

  public JSONType(String id, String body) {
    this.body = body;
    this.id = id;
  }

  public static void reload() {
    try {
      java.io.InputStream stream = Thread.currentThread().getContextClassLoader()
          .getResourceAsStream("message-type/json-messagetype.properties");
      if (stream == null) {
        stream = io.virtualan.cucumblan.props.ApplicationConfiguration.class.getClassLoader()
            .getResourceAsStream("message-type/json-messagetype.properties");
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
        return buildMessageType(body.toString(), contextParam);
      } else {

        message = io.virtualan.mapson.Mapson.buildMAPsonAsJson((java.util.Map) messages);
        body = new org.json.JSONObject(message);
        return buildMessageType(body.toString(), contextParam);
      }
    } catch (io.virtualan.mapson.exception.BadInputDataException | io.virtualan.cucumblan.message.exception.SkipMessageException exception) {
      throw new io.virtualan.cucumblan.message.exception.MessageNotDefinedException(
          exception.getMessage());
    }
  }

  //Mandatory
  public io.virtualan.cucumblan.message.type.MessageType buildConsumerMessage(
      org.apache.kafka.clients.consumer.ConsumerRecord<String, String> record,
      java.util.Map<String, Object> contextParam)
      throws io.virtualan.cucumblan.message.exception.SkipMessageException {
    return buildMessageType(record.value(), contextParam);
  }

  public String toString() {
    return "JSONMessageType{type='" + this.type + '\'' + ", id=" + this.id + ", body=" + this.body
        + '}';
  }

  public JSONType buildMessageType(String body, java.util.Map<String, Object> contextParam)
      throws io.virtualan.cucumblan.message.exception.SkipMessageException {
    if (jsonMessageTypeMapper != null && !jsonMessageTypeMapper.isEmpty()) {
      for (Entry entry : jsonMessageTypeMapper.entrySet()) {
        try {
          if (contextParam.get("EVENT_NAME") != null
              && entry.getKey().toString()
              .equalsIgnoreCase(contextParam.get("EVENT_NAME").toString())) {
              String identifier = buildkey(body, entry.getValue().toString());
              if (identifier != null) {
                  return new JSONType(identifier, body);
              }
          }
        } catch (Exception e) {
        }
      }
    }
    throw new io.virtualan.cucumblan.message.exception.SkipMessageException(body);
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
