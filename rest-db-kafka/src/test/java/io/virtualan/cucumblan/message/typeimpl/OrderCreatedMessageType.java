package io.virtualan.cucumblan.message.typeimpl;



public class OrderCreatedMessageType implements io.virtualan.cucumblan.message.type.MessageType<String, String> {
        private String type = "OrderMessageType";
        private String id;
        private String body;

        public OrderCreatedMessageType() {
        }

        public OrderCreatedMessageType(String id, String body) {
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
            return null;
        }

        public String getMessage() {
            return this.body;
        }

       //Mandatory
        public org.json.JSONObject getMessageAsJson() {
            return new org.json.JSONObject(this.body);
        }

        public io.virtualan.cucumblan.message.type.MessageType buildProducerMessage(Object messages) throws io.virtualan.cucumblan.message.exception.MessageNotDefinedException {
           //No need to provide details if we are not producing any message
           return null;
        }

        //Mandatory
        public io.virtualan.cucumblan.message.type.MessageType buildConsumerMessage(org.apache.kafka.clients.consumer.ConsumerRecord<String, String> record, String key, String body) throws io.virtualan.cucumblan.message.exception.MessageNotDefinedException {
            org.json.JSONObject object = new org.json.JSONObject(body);
            String id = object.getString("orderNumber");
            object.put("partitionId", record.partition());
            return new OrderCreatedMessageType(id, object.toString());
        }

        public String toString() {
            return "OrderCreatedMessageType{type='" + this.type + '\'' + ", id=" + this.id + ", body=" + this.body + '}';
        }
    }
