package io.virtualan.cucumblan.standard;

import io.virtualan.cucumblan.standard.StandardProcessing;
import io.virtualan.cucumblan.message.typeimpl.JSONMessage;


public class KafkaMessageAggregator implements StandardProcessing {


    @Override
    public String getType() {
        return "MSG_AGGREGATE";
    }

    @Override
    public Object responseEvaluator() {
        java.util.Map<String, Object> mapAggregator = io.virtualan.cucumblan.core.msg.kafka.MessageContext.getEventContextMap("TEST");
        int count = 0;
        if(mapAggregator != null) {
            for (java.util.Map.Entry<String, Object> entry : mapAggregator.entrySet()) {
                io.virtualan.cucumblan.message.typeimpl.JSONMessage jsonmsg = (io.virtualan.cucumblan.message.typeimpl.JSONMessage) entry.getValue();
                if ("doggie".equals(jsonmsg.getMessageAsJson().getString("name"))) {
                    count++;
                }
            }
        }
        org.json.JSONObject object = new org.json.JSONObject();
        object.put("totalMessageCount", count);
        return object;
    }


}
