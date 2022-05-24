package io.virtualan.cucumblan.message.typeimpl;


import com.jayway.jsonpath.JsonPath;
import io.virtualan.cucumblan.message.exception.SkipMessageException;
import io.virtualan.mapson.exception.BadInputDataException;
import org.apache.avro.Schema;
import org.apache.avro.data.TimeConversions;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.codehaus.plexus.util.StringOutputStream;

import java.io.*;
import java.nio.file.Files;
import java.util.Map.Entry;

public class AvroType implements
    io.virtualan.cucumblan.message.type.MessageType<String, GenericRecord> {

  private static final java.util.logging.Logger LOGGER = java.util.logging.Logger
      .getLogger(AvroType.class.getName());
  private static final com.google.protobuf.util.JsonFormat.Parser jsonParser = com.google.protobuf.util.JsonFormat
      .parser().ignoringUnknownFields();
  private static java.util.Properties avroMessageTypeMapper = new java.util.Properties();

  static {
    reload();
  }

  private String type = "AvroType";
  private String id;
  private String body;
  private GenericRecord originalBody;

  public AvroType() {
  }

  public AvroType(String id, String body, GenericRecord originalBody) {
    this.body = body;
    this.originalBody = originalBody;
    this.id = id;
  }

  public static void reload() {
    try {
      java.io.InputStream stream = Thread.currentThread().getContextClassLoader()
          .getResourceAsStream("message-type/avro-messagetype.properties");
      if (stream == null) {
        stream = io.virtualan.cucumblan.props.ApplicationConfiguration.class.getClassLoader()
            .getResourceAsStream("message-type/avro-messagetype.properties");
      }
      if (stream != null) {
        avroMessageTypeMapper.load(stream);
      } else {
        LOGGER.warning("unable to load avro-messagetype.properties");
      }
    } catch (Exception var1) {
      LOGGER.warning("avro-messagetype.properties not found");
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

  public GenericRecord getMessage() {
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
    } catch (BadInputDataException | SkipMessageException exception) {
      throw new io.virtualan.cucumblan.message.exception.MessageNotDefinedException(
          exception.getMessage());
    }
  }


  public AvroType serialize(String jsonbody,
                                java.util.Map<String, Object> contextParam)
          throws io.virtualan.cucumblan.message.exception.SkipMessageException {
    if (avroMessageTypeMapper != null && !avroMessageTypeMapper.isEmpty()) {
      for (Entry protoMessageTypeEntry : avroMessageTypeMapper.entrySet()) {
        try {
          if (contextParam.get("EVENT_NAME") != null
                  && protoMessageTypeEntry.getKey().toString()
                  .equalsIgnoreCase(contextParam.get("EVENT_NAME").toString())) {
            String[] messageTypeAndJsonPath = protoMessageTypeEntry.getValue().toString().split(";");
            if(messageTypeAndJsonPath.length ==2) {
             GenericRecord body = serialize(messageTypeAndJsonPath[0],
                      jsonbody);
              if (body != null) {
                Object identifier = com.jayway.jsonpath.JsonPath.read(jsonbody,
                        messageTypeAndJsonPath[1]);
                if (identifier != null) {
                  return new AvroType(
                          String.valueOf(identifier), jsonbody, body); //TODO);
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

  public GenericRecord serialize(String fileName, String messages) throws IOException {
//    InputStream input = new ByteArrayInputStream(messages.getBytes());
//    DataInputStream din = new DataInputStream(input);
//    Schema schema = Schema.parse(Files.readString(new File(Thread.currentThread().getContextClassLoader().getResource(fileName).getPath()).toPath()));
//    Decoder decoder = DecoderFactory.get().jsonDecoder(schema, din);
//    DatumReader<Object> reader = new GenericDatumReader<Object>(schema);
//    Object datum = reader.read(null, decoder);
//    GenericDatumWriter<Object>  w = new GenericDatumWriter<Object>(schema);
//    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//    Encoder e = EncoderFactory.get().binaryEncoder(outputStream, null);
//    w.write(datum, e);
//    e.flush();
//    return outputStream.toByteArray();
    String genericRecordStr = messages;
    Schema schema = Schema.parse(Files.readString(new File(Thread.currentThread().getContextClassLoader().getResource(fileName).getPath()).toPath()));
    DecoderFactory decoderFactory = new DecoderFactory();
    Decoder decoder = decoderFactory.jsonDecoder(schema, genericRecordStr);
    DatumReader<GenericData.Record> reader =
            new GenericDatumReader<>(schema);
    GenericRecord genericRecord = reader.read(null, decoder);
    return genericRecord;
  }

  //Mandatory
  public io.virtualan.cucumblan.message.type.MessageType buildConsumerMessage(
          org.apache.kafka.clients.consumer.ConsumerRecord<String, GenericRecord> record,
          java.util.Map<String, Object> contextParam)
          throws io.virtualan.cucumblan.message.exception.SkipMessageException {
    try {
      return deserialize(record.value(), contextParam);
    } catch (Exception e){
      throw new io.virtualan.cucumblan.message.exception.SkipMessageException("Not a valid message");
    }
  }

  public AvroType deserialize(GenericRecord body,
                                  java.util.Map<String, Object> contextParam)
          throws io.virtualan.cucumblan.message.exception.SkipMessageException {
    if (avroMessageTypeMapper != null && !avroMessageTypeMapper.isEmpty()) {

      for (Entry protoMessageTypeEntry : avroMessageTypeMapper.entrySet()) {
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
                  return new AvroType(
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

  public String deserialize(String classname, GenericRecord record) {
    try {
      OutputStream stringOutputStream = new StringOutputStream();
      GenericData.get().addLogicalTypeConversion(new TimeConversions.TimeMillisConversion());
      DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(record.getSchema());
      JsonEncoder encoder = EncoderFactory.get().jsonEncoder(record.getSchema(), stringOutputStream);
      writer.write(record, encoder);
      encoder.flush();
      return stringOutputStream.toString();
    } catch (Exception e) {
      throw new RuntimeException("Error creating read stream for JSON message", e);
    }
  }

  public String toString() {
    return "AvroMessageType{type='" + this.type + '\'' + ", id=" + this.id + ", body="
        + this.body + '}';
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
