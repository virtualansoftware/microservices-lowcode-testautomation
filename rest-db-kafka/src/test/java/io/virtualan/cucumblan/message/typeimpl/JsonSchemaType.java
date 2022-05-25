package io.virtualan.cucumblan.message.typeimpl;


import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import io.virtualan.cucumblan.props.util.StepDefinitionHelper;
import io.virtualan.cucumblan.props.util.UtilHelper;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class JsonSchemaType implements
    io.virtualan.cucumblan.message.type.MessageType<String, Object> {

  private static final java.util.logging.Logger LOGGER = java.util.logging.Logger
      .getLogger(JsonSchemaType.class.getName());
  private static final com.google.protobuf.util.JsonFormat.Parser jsonParser = com.google.protobuf.util.JsonFormat
      .parser().ignoringUnknownFields();
  private static java.util.Properties jsonSchemaMessageTypeMapper = new java.util.Properties();

  static {
    reload();
  }

  private String type = "JsonSchemaType";
  private String id;
  private String body;
  private Object originalBody;

  public JsonSchemaType() {
  }

  public JsonSchemaType(String id, String body, Object originalBody) {
    this.body = body;
    this.originalBody = originalBody;
    this.id = id;
  }

  public static void reload() {
    try {
      java.io.InputStream stream = Thread.currentThread().getContextClassLoader()
          .getResourceAsStream("message-type/jsonschema-messagetype.properties");
      if (stream == null) {
        stream = io.virtualan.cucumblan.props.ApplicationConfiguration.class.getClassLoader()
            .getResourceAsStream("message-type/jsonschema-messagetype.properties");
      }
      if (stream != null) {
        jsonSchemaMessageTypeMapper.load(stream);
      } else {
        LOGGER.warning("unable to load jsonschema-messagetype.properties");
      }
    } catch (Exception var1) {
      LOGGER.warning("jsonschema-messagetype.properties not found");
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

  public Object getMessage() {
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
      org.apache.kafka.clients.consumer.ConsumerRecord<String, Object> record,
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


    public JsonSchemaType serialize(String jsonbody,
                                    java.util.Map<String, Object> contextParam)
        throws io.virtualan.cucumblan.message.exception.SkipMessageException {
        if (jsonSchemaMessageTypeMapper != null && !jsonSchemaMessageTypeMapper.isEmpty()) {
            for (Entry protoMessageTypeEntry : jsonSchemaMessageTypeMapper.entrySet()) {
                try {
                    if (contextParam.get("EVENT_NAME") != null
                        && protoMessageTypeEntry.getKey().toString()
                        .equalsIgnoreCase(contextParam.get("EVENT_NAME").toString())) {
                      String[] messageTypeAndJsonPath = protoMessageTypeEntry.getValue().toString().split(";");
                      if(messageTypeAndJsonPath.length ==2) {
                        Object body = serialize(messageTypeAndJsonPath[0],
                                jsonbody);
                        if (body != null) {
                          Object identifier = JsonPath.read(jsonbody,
                                  messageTypeAndJsonPath[1]);
                          if (identifier != null) {
                            return new JsonSchemaType(
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

  public JsonSchemaType deserialize(Object body,
                                    java.util.Map<String, Object> contextParam)
      throws io.virtualan.cucumblan.message.exception.SkipMessageException {
    if (jsonSchemaMessageTypeMapper != null && !jsonSchemaMessageTypeMapper.isEmpty()) {

      for (Entry protoMessageTypeEntry : jsonSchemaMessageTypeMapper.entrySet()) {
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
                  return new JsonSchemaType(
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

  public Object serialize(String classname, String messages)
      throws ClassNotFoundException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.io.IOException {
    Class<?> clazz = Class.forName(classname);
    Gson gson = new Gson();
    return gson.fromJson(messages, clazz);
  }

  public String deserialize(String classname, Object payload) {
    try {
      Gson gson = new Gson();
      return gson.toJson(payload, LinkedHashMap.class);
    } catch (Exception e) {
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
